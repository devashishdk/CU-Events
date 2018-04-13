package com.inventano.cuworkshop;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class SearchActivityGrid extends AppCompatActivity {

    Toolbar toolbar;
    Button refresh;
    TextView internet;
    ProgressBar progressBar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    postAdapterGrid adapter;
    List<Item> items = new ArrayList<>();
    Boolean isScrolling = false;
    String token = "";
    EditText searchQuerry;
    Button searchButton;
    ViewPager viewPager;
    RelativeLayout pagerView;
    LinearLayout searchLayout;
    TextView notFound;
    Timer timer;
    int currentItems,totalItems,scrolledOutItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_grid);
        setUpToolBar();
        recyclerView = (RecyclerView) findViewById(R.id.postList);
        searchLayout = (LinearLayout) findViewById(R.id.searchLayout);
        notFound = (TextView) findViewById(R.id.notfound);
        manager = new GridLayoutManager(this,2);
        adapter = new postAdapterGrid(this,items);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        pagerView = (RelativeLayout) findViewById(R.id.pagerView);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                viewPager.post(new Runnable(){

                    @Override
                    public void run() {
                        viewPager.setCurrentItem((viewPager.getCurrentItem()+1)% 3);
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 3000, 3000);


        searchQuerry = (EditText) findViewById(R.id.search);
        searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent1 = new Intent(SearchActivityGrid.this,SearchActivity.class);
                intent1.putExtra("searchItem", searchQuerry.getText().toString());
                startActivity(intent1);
            }
        });
        refresh = (Button) findViewById(R.id.refresh);
        internet = (TextView) findViewById(R.id.internet);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case (R.id.home):
                        drawerLayout.closeDrawers();
                        Intent intent = new Intent(SearchActivityGrid.this,GridLayout.class);
                        startActivity(intent);
                        finish();
                        //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                        break;
                    case (R.id.contact):
                        drawerLayout.closeDrawers();
                        Intent intent2 = new Intent(SearchActivityGrid.this,SocialActivity.class);
                        startActivity(intent2);
                        //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                        break;
                    case (R.id.about):
                        drawerLayout.closeDrawers();
                        Intent intent1 = new Intent(SearchActivityGrid.this,AboutActivity.class);
                        startActivity(intent1);
                        //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                        break;
                    case (R.id.share):
                        drawerLayout.closeDrawers();
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "Information for all latest Government Job Openings :- http://www.bloggertemplate.com/");
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                        //overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                        break;
                    case (R.id.workshop):
                        drawerLayout.closeDrawers();
                        Intent intentWorkshop = new Intent(SearchActivityGrid.this,SearchActivityGrid.class);
                        intentWorkshop.putExtra("searchItem", "workshop");
                        startActivity(intentWorkshop);
                        break;
                    case (R.id.seminars):
                        drawerLayout.closeDrawers();
                        Intent intentSeminars = new Intent(SearchActivityGrid.this,SearchActivityGrid.class);
                        intentSeminars.putExtra("searchItem", "seminar");
                        startActivity(intentSeminars);
                        break;
                    case (R.id.competitions):
                        drawerLayout.closeDrawers();
                        Intent intentCompetitions = new Intent(SearchActivityGrid.this,SearchActivityGrid.class);
                        intentCompetitions.putExtra("searchItem", "competition");
                        startActivity(intentCompetitions);
                        break;
                }
                return false;
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrolling = true;
                }
                searchLayout.setVisibility(GONE);
                pagerView.setVisibility(GONE);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrolledOutItems = manager.findFirstVisibleItemPosition();

                if(isScrolling && (currentItems + scrolledOutItems == totalItems))
                {
                    isScrolling = false;
                    try{
                        getData();
                    }
                    catch (Exception e)
                    {

                    }
                }
            }
        });

        getData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_grid,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case (R.id.changeLayout):
                if(item.getTitle() == "Card") {
                    drawerLayout.closeDrawers();
                    Intent intent = new Intent(SearchActivityGrid.this,SearchActivity.class);
                    intent.putExtra("searchItem", getIntent().getStringExtra("searchItem"));
                    startActivity(intent);
                    finish();
                    /*LinearLayoutManager mLinearLayoutManagerVertical = new LinearLayoutManager(this); // (Context context)
                    mLinearLayoutManagerVertical.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(mLinearLayoutManagerVertical);
                    */
                    item.setTitle("Grid");

                }
                else
                {
                    drawerLayout.closeDrawers();
                    /*GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 2); // (Context context, int spanCount)
                    recyclerView.setLayoutManager(mGridLayoutManager);

                    */
                    Intent intent = new Intent(SearchActivityGrid.this,SearchActivity.class);
                    intent.putExtra("searchItem", getIntent().getStringExtra("searchItem"));
                    startActivity(intent);
                    item.setTitle("Card");
                    finish();

                }
                finish();

                break;

            case (R.id.searchKey):
                if (searchLayout.getVisibility() == GONE) {
                    searchLayout.setVisibility(VISIBLE);
                }
                else
                {
                    searchLayout.setVisibility(GONE);
                }
                break;
            case (R.id.exit):
                drawerLayout.closeDrawers();
                finish();
                System.exit(0);
                break;
            case (R.id.settings):
                drawerLayout.closeDrawers();
                break;
            case R.id.menuLogout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
        }
        return true;
    }

    void setUpToolBar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle =  new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
    public void getData()
    {
        String url = BloggerAPI.url + "search?q=" + getIntent().getStringExtra("searchItem") +"&key=" + BloggerAPI.key;
        if (!(token.equals(""))){
            url = url+ "&pageToken="+ token;
        }
        if(token == null){
            return;
        }
        Call<PostList> postList = BloggerAPI.getService().getPostList(url);
        progressBar = (ProgressBar) findViewById(R.id.homeProgress);

        postList.enqueue(new Callback<PostList>() {
            @Override
            public void onResponse(Call<PostList> call, Response<PostList> response) {
                PostList list = response.body();
                token = list.getNextPageToken();
                try {
                    if(items!= null) {
                        items.addAll(list.getItems());
                    }
                    else
                        notFound.setVisibility(VISIBLE);
                    adapter.notifyDataSetChanged();

                    //recyclerView.setAdapter(new postAdapter(MainActivity.this,list.getItems()));
                }
                catch (Exception e)
                {
                    notFound.setVisibility(VISIBLE);
                }
                progressBar.setVisibility(GONE);
            }

            @Override
            public void onFailure(Call<PostList> call, Throwable t) {
                Toast.makeText(SearchActivityGrid.this, "Error Occured", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(GONE);
                refresh.setVisibility(VISIBLE);
                internet.setVisibility(VISIBLE);
            }
        });
    }

    public void onClickRefresh(View view)
    {
        getData();
        progressBar.setVisibility(VISIBLE);
        internet.setVisibility(GONE);
        refresh.setVisibility(GONE);
    }
    public void onClickClosePager(View view)
    {
        pagerView.setVisibility(GONE);
    }

}

//String url = BloggerAPI.url + "search?q=" + getIntent().getStringExtra("searchItem") +"&key=" + BloggerAPI.key;

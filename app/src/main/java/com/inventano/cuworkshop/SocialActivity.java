package com.inventano.cuworkshop;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class SocialActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpToolBar();
        setContentView(R.layout.activity_social);
    }

    public void onClickBack(View view)
    {
        finish();
    }

    void setUpToolBar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        setSupportActionBar(toolbar);
    }

    public void onWeb(View view)
    {
        Intent intent = new Intent(SocialActivity.this,DetailActivity.class);
        intent.putExtra("url", "http://www.cuworkshop.tk/");
        startActivity(intent);

    }
    public void onFb(View view)
    {
        Intent intent = new Intent(SocialActivity.this,DetailActivity.class);
        intent.putExtra("url", "https://www.facebook.com/CU-Events-384950461971426/");
        startActivity(intent);
    }
    public void onTwitter(View view)
    {
        Intent intent = new Intent(SocialActivity.this,DetailActivity.class);
        intent.putExtra("url", "http://www.bloggertemplate.com/");
        startActivity(intent);
    }

}

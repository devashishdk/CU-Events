package com.inventano.cuworkshop;

        import android.content.Context;
        import android.content.Intent;
        import android.support.v4.view.PagerAdapter;
        import android.support.v4.view.ViewPager;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.Toast;


public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private Integer [] images = {R.drawable.workshop_banner,R.drawable.seminars_banner,R.drawable.competition_banner};

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout, null);
        final ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        imageView.setImageResource(images[position]);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position==0)
                {
                    Intent intentSeminars = new Intent(context,SearchActivity.class);
                    intentSeminars.putExtra("searchItem", "workshop");
                    context.startActivity(intentSeminars);
                }
                else if(position==1)
                {
                    Intent intentSeminars = new Intent(context,SearchActivity.class);
                    intentSeminars.putExtra("searchItem", "seminar");
                    context.startActivity(intentSeminars);
                }
                else
                {
                    Intent intentSeminars = new Intent(context,SearchActivity.class);
                    intentSeminars.putExtra("searchItem", "competition");
                    context.startActivity(intentSeminars);
                }
            }
        });

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}
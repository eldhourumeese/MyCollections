package com.example.mycollections.view;

import android.app.SearchManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycollections.R;
import com.example.mycollections.databinding.ActivityMainBinding;
import com.example.mycollections.databinding.RowHomeGridBinding;
import com.example.mycollections.model.Content;
import com.example.mycollections.repository.MyRepositoryImp;
import com.example.mycollections.viewmodel.MyDataViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding = null;
    private int mPageCount = 0;
    HomeDataAdapter mAdapter;
    private ArrayList<Content> homeMenuItemsList = new ArrayList<>();
    private int gridCount = 3;
    private int totalItems = 0;
    private boolean isLoading = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(getViewModel());
        binding.executePendingBindings();
        initToolbar();
        setAdapter();
        getViewModel().setRepository(new MyRepositoryImp(this));
        getViewModel().getDataList(mPageCount + 1);
        getViewModel().getErrorMsg();
        observeLiveData();
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void setAdapter() {
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), gridCount));
        mAdapter = new HomeDataAdapter(homeMenuItemsList, this);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setAdapter(mAdapter);
    }

    private void observeLiveData() {

        getViewModel().response.observe(this, response -> {
            if (response != null) {
                mPageCount++;
                setTitle(response.getPage().getTitle());
                totalItems = response.getPage().getTotal_content_items();
                ArrayList<Content> arrayList = response.getPage().getContent_items().getContent();

                homeMenuItemsList.addAll(arrayList);
                if (homeMenuItemsList != null && homeMenuItemsList.size() > 0) {
                    setUpRecyclerView(homeMenuItemsList);
                }
                isLoading = false;
            }
        });


        getViewModel().errorMessage.observe(this, errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public void onConfigurationChanged(Configuration config) {//when orientation change, adjust width columns
        super.onConfigurationChanged(config);
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridCount = 7;
        } else if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
            gridCount = 3;
        }
        setUpRecyclerView(homeMenuItemsList);
    }


   //set adapter to recyclerview
    private void setUpRecyclerView(ArrayList<Content> menuItemsList) {
        binding.recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), gridCount));
        mAdapter = new HomeDataAdapter(menuItemsList, this);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setAdapter(mAdapter);

        binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0/*|| dy < 0 && !scrollShade.isShown()*/)
                    binding.scrollShade.setVisibility(View.VISIBLE);
                else
                    binding.scrollShade.setVisibility(View.GONE);

                if (!isLoading) {
                    if (homeMenuItemsList.size() < totalItems) {
                        isLoading = true;
                        getViewModel().getDataList(mPageCount + 1);
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });


    }


    // searchview on toolbar
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        SearchView searchView = (SearchView) searchItem.getActionView();


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (null != searchManager) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                filterList(query);

                return true;

            }

        });
        return true;
    }


    // get corresponding image for menu
    private int getMenuImage(String menu_id) {
        switch (menu_id) {
            case "poster1.jpg":
                return R.drawable.poster1;
            case "poster2.jpg":
                return R.drawable.poster2;
            case "poster3.jpg":
                return R.drawable.poster3;
            case "poster4.jpg":
                return R.drawable.poster4;
            case "poster5.jpg":
                return R.drawable.poster5;
            case "poster6.jpg":
                return R.drawable.poster6;
            case "poster7.jpg":
                return R.drawable.poster7;
            case "poster8.jpg":
                return R.drawable.poster8;
            case "poster9.jpg":
                return R.drawable.poster9;
            default:
                return R.drawable.placeholder_for_missing_posters;
        }
    }


    // adapter for menus in home page
    class HomeDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private ArrayList<Content> arrayList;
        private Context context;


        HomeDataAdapter(ArrayList<Content> arrayList, Context context) {
            this.arrayList = arrayList;
            this.context = context;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RowHomeGridBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.row_home_grid, parent, false);
            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int pos) {

            ((ViewHolder) viewHolder).bindData(arrayList.get(pos), pos);

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {

            RowHomeGridBinding binding;

            ViewHolder(RowHomeGridBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            void bindData(final Content content, final int position) {
                content.setImage(getMenuImage(content.getPoster_image()));
                binding.setData(content);
            }
        }

    }


    //filter by search query
    private void filterList(String query) {

        query = query.toLowerCase();

        ArrayList<Content> arrayList = new ArrayList<>();

        if (homeMenuItemsList != null) {
            if (query.length() < 3 && query.length() > 0) {//check query  length is enough or not

                arrayList = homeMenuItemsList;//if query is not enough whole item will show
                Toast toast = Toast.makeText(this, "Enter minimum 3 letters", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0, 0);
                toast.show();

            } else {
                for (int i = 0; i < homeMenuItemsList.size(); i++) {//check each item
                    Content obj = homeMenuItemsList.get(i);
                    boolean filter = false;
                    if (obj.getName() != null)
                        if (obj.getName().toLowerCase().contains(query)) {//make name lowercase to compare with query(query alreay converted to lowercase
                            filter = true;//matched
                        }
                    if (filter) {
                        arrayList.add(obj);//add matched item to list
                    }

                }

            }
        }
        setUpRecyclerView(arrayList);

    }

    private MyDataViewModel getViewModel() {
        return new ViewModelProvider(this).get(MyDataViewModel.class);
    }

}

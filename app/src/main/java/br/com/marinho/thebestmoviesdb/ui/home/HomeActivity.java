package br.com.marinho.thebestmoviesdb.ui.home;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import java.util.ArrayList;

import br.com.marinho.thebestmoviesdb.R;
import br.com.marinho.thebestmoviesdb.infra.App;
import br.com.marinho.thebestmoviesdb.infra.DeviceHelper;
import br.com.marinho.thebestmoviesdb.repository.Model.Movie;
import br.com.marinho.thebestmoviesdb.ui.ViewUtils;
import br.com.marinho.thebestmoviesdb.ui.home.adapter.HomeAdapter;
import br.com.marinho.thebestmoviesdb.ui.home.interfaces.IHomeView;

public class HomeActivity extends AppCompatActivity implements IHomeView{

    static RecyclerView recyclerViewMovie;
    LinearLayoutManager layoutManager;
    View parentView;
    ArrayList<Movie> listMovie;
    int position = 0;
    HomeAdapter adapterMovie;
    boolean addMoreItems = false;
    boolean isLoading = false;
    boolean endItems = false;
    int page = HomePresenter.FIRST_PAGE;
    ProgressDialog progressDialog;
    SwipeRefreshLayout swipeContainer;

    //Search View
    AppBarLayout appBar;
    boolean touchInsideSearchview = false;
    static SearchView searchView;
    static Button btnCloseSearch;
//    static MenuItem cleanSearch;
    static Toolbar toolbar;
    static boolean searchIsOpen = false;
    boolean isTextSearch = false;
    String searchQuery;

    HomePresenter homePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupView();

        setupSwipeView();
        swipeContainer.setRefreshing(true);

        loadMovie();
    }

    private void loadMovie(){
        if(isTextSearch){
            homePresenter.searchMovie(searchQuery, page);
        }else if (App.getMovies() != null) {
            fillMovieList(App.getMovies());
        }else {
            if (DeviceHelper.isNetworkAvailable(App.getContext())) {
                homePresenter.loadMovie(page);
            } else {
                Snackbar.make(parentView, getString(R.string.noNetworOfflinekMsg),
                        Snackbar.LENGTH_LONG).show();
            }
        }
    }

    private void setupView(){
        recyclerViewMovie = (RecyclerView) findViewById(R.id.recyclerMovie);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        parentView = findViewById(R.id.parentLayout);

        homePresenter = new HomePresenter(this);
    }

    public void setupSwipeView() {
        //position = 0;
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                scrollToTop();
                swipeContainer.setRefreshing(true);
                page = HomePresenter.FIRST_PAGE;
                listMovie = new ArrayList<>();
                isTextSearch = false;
                loadMovie();
                Log.d("SwipeRefresh", "Refreshing");
            }
        });

        layoutManager = new LinearLayoutManager(App.getContext());
        recyclerViewMovie.setLayoutManager(layoutManager);
        listMovie = new ArrayList<>();

        recyclerViewMovie.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int total = layoutManager.getItemCount();
                int firstVisibleItemCount = layoutManager.findFirstVisibleItemPosition();
                int lastVisibleItemCount = layoutManager.findLastVisibleItemPosition();
                isLoading = swipeContainer.isRefreshing();

                if (!isLoading && !endItems) {
                    if (total > 0) {
                        if ((total - 1) == lastVisibleItemCount) {
                            if (DeviceHelper.isNetworkAvailable(App.getContext())) {
                                Log.i("final", "total " + total + "lastVisibleItemCount " +
                                        lastVisibleItemCount + "page: " + page);
                                page++;
                                addMoreItems = true;

                                swipeContainer.setRefreshing(true);
                                if(isTextSearch){
                                    homePresenter.searchMovie(searchQuery, page);
                                }else {
                                    homePresenter.loadMovie(page);
                                }
                            } else {
                                Snackbar.make(parentView, getString(R.string.noNetworOfflinekMsg),
                                        Snackbar.LENGTH_LONG).show();

                                if (App.getMovies() != null) {
                                    fillMovieList(App.getMovies());
                                }
                            }
                        } else if (firstVisibleItemCount == (listMovie.size() - total)) {
                            Log.i("final", "firstVisibleItemCount" + firstVisibleItemCount);
                        }
                    }
                }
            }
        });
    }

    public static void scrollToTop() {
        LinearLayoutManager layoutManager = (LinearLayoutManager)
                recyclerViewMovie.getLayoutManager();
        layoutManager.scrollToPositionWithOffset(0, 0);
    }

    private void fillMovieList(ArrayList<Movie> movieList) {
        swipeContainer.setRefreshing(false);

        if (movieList.size() != 0) {
            if (addMoreItems) {
                position = listMovie.size();
                listMovie.addAll(position, movieList);

                adapterMovie = new HomeAdapter(listMovie, parentView, this);
                recyclerViewMovie.setAdapter(adapterMovie);
                adapterMovie.notifyDataSetChanged();
                layoutManager.scrollToPosition(position);
            } else {
                listMovie.addAll(position, movieList);

                adapterMovie = new HomeAdapter(listMovie, parentView, this);
                recyclerViewMovie.setAdapter(adapterMovie);
            }
        } else {
            endItems = true;
        }
    }

    @Override
    public void loadMovies(ArrayList<Movie> movies) {
        fillMovieList(movies);
    }

    @Override
    public void showProgress() {
        progressDialog = ProgressDialog.show(this,
                getResources().getString(R.string.home_progress_title),
                getResources().getString(R.string.home_progress_message));
    }

    @Override
    public void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showError(String message) {
        Snackbar.make(parentView, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_home, menu);

        btnCloseSearch = (Button) findViewById(R.id.btnArrow);

//        cleanSearch = menu.findItem(R.id.cleanSearch);
//        cleanSearch.setVisible(false);

        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_searchable_activity));

        setViewSearch();

        return true;
    }

    private void setViewSearch() {
        SearchManager searchManager = (SearchManager)  getSystemService(getApplicationContext().SEARCH_SERVICE);

        ComponentName cn = new ComponentName(this, HomeActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(cn));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                closeSearchview();
                toolbar.collapseActionView();
                searchQuery = query;
                isTextSearch = true;
                loadMovie();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                if(newText.isEmpty()){
//                    cleanSearch.setVisible(false);
//                }else{
//                    cleanSearch.setVisible(true);
//                }

                return false;
            }
        });

        searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                closeSearchview();
                toolbar.collapseActionView();
            }
        });

//        createSearchPopup();
        setBtnCloseSearch();
        setCloseSearchOnScrollPage();
    }

    private void setCloseSearchOnScrollPage() {
        appBar = (AppBarLayout) findViewById(R.id.appBarLayout);
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(verticalOffset != 0){
                    closeSearchview();
                    toolbar.collapseActionView();
                }
            }
        });
    }

    public static void setBtnCloseSearch() {
        btnCloseSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSearchview();
                toolbar.collapseActionView();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_searchable_activity:
                touchInsideSearchview = true;

                openSearchView();

                MenuItemCompat.setOnActionExpandListener(item, new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        searchIsOpen = true;
                        setSearchIsOpen(searchIsOpen);
                        setBtnCloseSearch();
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        closeSearchview();
                        searchIsOpen = false;
                        setSearchIsOpen(searchIsOpen);
                        return true;
                    }
                });

                return true;

//            case R.id.cleanSearch:
//                searchView.setQuery("", false);
////                popupSearchOptions.showAsDropDown(toolbar);
//                return false;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openSearchView() {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.res_fadein);
        searchView.startAnimation(animation);
        searchView.requestFocus();

        btnCloseSearch.setVisibility(View.VISIBLE);
        setBtnCloseSearch();

        toolbar.setPadding(0,0,0,0);

//        popupSearchOptions.showAsDropDown(toolbar);
    }

    private static void closeSearchview() {
        searchView.setQuery("", false);
        searchView.clearFocus();
        searchView.setIconified(true);

        btnCloseSearch.setVisibility(View.INVISIBLE);

//        cleanSearch.setVisible(false);

        ViewUtils.setToolbarPadding(toolbar, App.getContext());
//        popupSearchOptions.dismiss();
    }

//    private void createSearchPopup(){
//        popupSearchOptions = new PopupWindow(TabsBaseActivity.this);
//        View layout = getLayoutInflater().inflate(R.layout.search_popup, null);
//
//        popupSearchOptions.setContentView(layout);
//        popupSearchOptions.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
//        popupSearchOptions.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
//        popupSearchOptions.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//        // Closes the popupSearchOptions window when touch outside of it - when looses focus
//        popupSearchOptions.setOutsideTouchable(true);
//
//        popupSearchOptions.setTouchInterceptor(new View.OnTouchListener(){
//
//            public boolean onTouch(View v, MotionEvent event){
//                if (event.getAction() == MotionEvent.ACTION_OUTSIDE){
//                    if(!touchInsideSearchview){
//                        closeSearchview();
//                        toolbar.collapseActionView();
//                    }
//                    return true;
//                }
//
//                return false;
//            }
//        });
//
//        Button btnVoice = (Button) popupSearchOptions.getContentView().findViewById(R.id.btnVoice);
//        Button btnBarcode = (Button) popupSearchOptions.getContentView().findViewById(R.id.btnBarcode);
//
//        btnVoice.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showProgress();
//
//           /* Call Activity for Voice Input */
//                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "free_form");
//
//                searchView.setQuery("", false);
//
//                try {
//                    hideProgress();
//                    startActivityForResult(intent, 1);
//                } catch (ActivityNotFoundException a) {
//                    hideProgress();
//                    Toast.makeText(context, "Oops! Este dispositivo não suporta busca por voz", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//
//        btnBarcode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showProgress();
//
//                Intent intent = new Intent(getApplicationContext(),BarCodeActivity.class);
//                hideProgress();
//                startActivityForResult(intent, RC_BARCODE_CAPTURE);
//            }
//        });
//    }


    public void closeSearchOnTouch(View v){
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                touchInsideSearchview = false;
                return false;
            }
        });
    }

    @Override
    public MenuInflater getMenuInflater() {
        return super.getMenuInflater();
    }

    public static void setSearchIsOpen(Boolean searchIsOpen) { HomeActivity.searchIsOpen = searchIsOpen; }
}

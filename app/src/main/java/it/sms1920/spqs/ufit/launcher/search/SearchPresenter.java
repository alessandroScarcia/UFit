package it.sms1920.spqs.ufit.launcher.search;

/**
 * Presenter for the View 'SearchActivity'.
 * Contains business logic for 'Search Exercise' functionality.
 */
public class SearchPresenter implements SearchContract.Presenter {

    private final SearchContract.View view;

    public SearchPresenter(SearchContract.View view) {
        this.view = view;
    }

    @Override
    public void onBackPressed() {
        view.back();
    }

    @Override
    public void onQueryTextChanged(final String keyword) {
        view.notifyQueryTextChangedToAdapter(keyword.trim());
    }

    @Override
    public void onItemClicked(int position) {
        view.showExercise(position);
    }

}

package it.sms1920.spqs.ufit.presenter;

import it.sms1920.spqs.ufit.contract.iSearch;

/**
 * Presenter for the View 'SearchActivity'.
 * Contains business logic for 'Search Exercise' functionality.
 */
public class SearchPresenter implements iSearch.Presenter {

    private final iSearch.View view;

    public SearchPresenter(iSearch.View view) {
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

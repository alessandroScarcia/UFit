//TODO rinominare il nome di sto file in qualcosa di più azzeccato

package it.sms1920.spqs.ufit.presenter;

import it.sms1920.spqs.ufit.contract.SearchContract;

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
    public void onQueryTextChanged(String query) {
        view.notifyQueryTextChangedToAdapter(query.trim());
    }

}

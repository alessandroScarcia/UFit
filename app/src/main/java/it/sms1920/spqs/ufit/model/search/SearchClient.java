package it.sms1920.spqs.ufit.model.search;

/**
 * Interface to be implemented by every Presenter that interacts SearchExercise
 */
public interface SearchClient {
    /**
     * Method called by SearchExercise when the search result has been fetched from firebase.
     */
    void notifyResultListReady();
}

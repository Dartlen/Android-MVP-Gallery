package by.test.dartlen.gallery.util;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/***
 * Created by Dartlen on 03.11.2017.
 */
public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

    private LinearLayoutManager layoutManager;
    private int currentpage=1;

    private int visibleItemCount;
    private int totalItemCount;
    private int firstVisibleItemPosition;

    protected PaginationScrollListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = layoutManager.getChildCount();
        totalItemCount = layoutManager.getItemCount();
        firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        if (!isLoading() && !isLastPage()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= 2) {
                currentpage++;
                loadMoreItems(currentpage);
            }
        }
    }

    protected abstract void loadMoreItems(int currentpage);

    public abstract boolean isLastPage();

    public abstract boolean isLoading();


}

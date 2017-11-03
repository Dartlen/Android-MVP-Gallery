package by.test.dartlen.gallery.gallery;

/***
 * Created by Dartlen on 30.10.2017.
 */

import android.os.AsyncTask;

import java.util.concurrent.TimeUnit;

public class AddItemsImage extends AsyncTask<Void, Void, Void> {

    private OnAddItemListener addItemToAdapter;

    public AddItemsImage(OnAddItemListener addItemToAdapter) {
        this.addItemToAdapter = addItemToAdapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        addItemToAdapter.onStartImage();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        addItemToAdapter.onFinishImage();
    }

    public interface OnAddItemListener {
        void onStartImage();
        void onFinishImage();
    }
}

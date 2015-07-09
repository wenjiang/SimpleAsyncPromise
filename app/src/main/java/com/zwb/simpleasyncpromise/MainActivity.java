package com.zwb.simpleasyncpromise;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.zwb.async.DefaultDeferredManager;
import com.zwb.async.DeferredManager;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DeferredManager manager = new DefaultDeferredManager();
        manager.when(new Callable<String>() {
            @Override
            public String call() throws Exception {
                final int DELAY = 3;
                Thread.sleep(TimeUnit.SECONDS.toMillis(DELAY));
                return "hello";
            }
        }).then(new DoneCallback<String>() {
            @Override
            public void onDone(String result) {
                Log.e("MainActivity", result + " world");
            }
        }).fail(new FailCallback<Throwable>() {
            @Override
            public void onFail(Throwable exception) {
                Log.e("MainActivity", exception.toString());
            }
        }).always(new AlwaysCallback() {

            @Override
            public void onAlways(Promise.State state, Object resolveResult, Object rejectResult) {
                Log.e("MainActivity", "我在这里");
            }
        }).progress(new ProgressCallback() {
            @Override
            public void onProgress(Object progress) {
                Log.e("MainActivity", progress + "");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

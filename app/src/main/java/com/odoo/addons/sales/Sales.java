package com.odoo.addons.sales;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.odoo.R;
import com.odoo.addons.sales.models.SaleOrder;
import com.odoo.core.orm.ODataRow;
import com.odoo.core.support.addons.fragment.BaseFragment;
import com.odoo.core.support.addons.fragment.ISyncStatusObserverListener;
import com.odoo.core.support.drawer.ODrawerItem;
import com.odoo.core.support.list.OCursorListAdapter;
import com.odoo.core.utils.OControls;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lavrentievd on 15.12.2017.
 */

public class Sales extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor>,
        ISyncStatusObserverListener, SwipeRefreshLayout.OnRefreshListener, OCursorListAdapter.OnViewBindListener {

    public static final String TAG = Sales.class.getSimpleName();

    private View mView;
    private ListView listView;
    private OCursorListAdapter listAdapter;
    private boolean syncRequested = false;


    @Override
    public List<ODrawerItem> drawerMenus(Context context) {
        List<ODrawerItem> menu = new ArrayList<>();
        menu.add(new ODrawerItem(TAG).setTitle("Sales").setGroupTitle().setInstance(new Sales()));
        menu.add(new ODrawerItem(TAG).setIcon(R.drawable.ic_action_customers).setTitle("Customers").setInstance(new Sales()));
        menu.add(new ODrawerItem(TAG).setIcon(R.drawable.ic_action_content_export).setTitle("Quotations").setInstance(new Sales()));
        menu.add(new ODrawerItem(TAG).setIcon(R.drawable.ic_action_company).setTitle("Sales Orders").setInstance(new Sales()));
        menu.add(new ODrawerItem(TAG).setIcon(R.drawable.ic_action_suppliers).setTitle("Products").setInstance(new Sales()));
        return menu;
    }

    @Override
    public Class<SaleOrder> database() {
        return SaleOrder.class;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.common_listview, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;
        listView = (ListView) mView.findViewById(R.id.listview);

        listAdapter = new OCursorListAdapter(getActivity(), null, R.layout.sale_row_item);
        //listAdapter = new OCursorListAdapter(getActivity(), null, android.R.layout.simple_list_item_1);
        listView.setAdapter(listAdapter);

        listAdapter.setOnViewBindListener(this);

        setHasSyncStatusObserver(TAG, this, db());
        getLoaderManager().initLoader(0, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), db().uri(), null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        listAdapter.changeCursor(data);
        if (data.getCount() > 0) {
            OControls.setGone(mView, R.id.loadingProgress);
            OControls.setVisible(mView, R.id.swipe_container);
            OControls.setGone(mView, R.id.data_list_no_item);
            setHasSwipeRefreshView(mView, R.id.swipe_container, this);
        } else {
            OControls.setGone(mView, R.id.loadingProgress);
            OControls.setGone(mView, R.id.swipe_container);
            OControls.setVisible(mView, R.id.data_list_no_item);
            setHasSwipeRefreshView(mView, R.id.data_list_no_item, this);
            OControls.setText(mView, R.id.title, "No Tasks found");
            OControls.setText(mView, R.id.subTitle, "Swipe to check new tasks");
        }
        if (db().isEmptyTable() && !syncRequested) {
            // Request for sync
            syncRequested = true;
            onRefresh();
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        listAdapter.changeCursor(null);
    }

    @Override
    public void onRefresh() {
        if (inNetwork()) {
            parent().sync().requestSync(SaleOrder.AUTHORITY);
        }
    }

    @Override
    public void onStatusChange(Boolean refreshing) {
        if(refreshing){
            getLoaderManager().restartLoader(0, null, this);
        }
    }

    @Override
    public void onViewBind(View view, Cursor cursor, ODataRow row) {
        try {
//            OControls.setText(view, android.R.id.text1, row.getString("name"));

//            OControls.setText(view, android.R.id.text1, (row.getString("partner_name").equals("false"))
//                    ? "" : row.getString("partner_name"));

//            OControls.setText(view, android.R.id.text1, (row.getString("user_name").equals("false"))
//                    ? "" : row.getString("user_name"));

            OControls.setText(view, R.id.name, row.getString("name"));
            OControls.setText(view, R.id.customer_sale_order, (row.getString("partner_name").equals("false"))
                    ? "" : row.getString("partner_name"));
            OControls.setText(view, R.id.saleperson, (row.getString("user_name").equals("false") ? " "
                    : row.getString("user_name")));


        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

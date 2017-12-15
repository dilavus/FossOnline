package com.odoo.addons.sales;


import android.content.Context;

import com.odoo.R;
import com.odoo.addons.sales.models.SaleOrder;
import com.odoo.core.support.addons.fragment.BaseFragment;
import com.odoo.core.support.drawer.ODrawerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lavrentievd on 15.12.2017.
 */

public class Sales extends BaseFragment {
    public static final String TAG = Sales.class.getSimpleName();

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

}

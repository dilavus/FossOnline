package com.odoo.addons.sales.services;

import android.content.Context;
import android.os.Bundle;

import com.odoo.addons.sales.Sales;
import com.odoo.addons.sales.models.SaleOrder;
import com.odoo.core.service.OSyncAdapter;
import com.odoo.core.service.OSyncService;
import com.odoo.core.support.OUser;

/**
 * Created by lavrentievd on 15.12.2017.
 */

public class SaleSyncService extends OSyncService {
    public static final String TAG = SaleSyncService.class.getSimpleName();

    @Override
    public OSyncAdapter getSyncAdapter(OSyncService service, Context context) {
        return new OSyncAdapter(context, SaleOrder.class, service, true);
    }

    @Override
    public void performDataSync(OSyncAdapter adapter, Bundle extras, OUser user) {
        adapter.syncDataLimit(80);
    }
}

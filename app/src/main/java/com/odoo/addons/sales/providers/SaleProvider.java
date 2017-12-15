package com.odoo.addons.sales.providers;

import com.odoo.addons.sales.models.SaleOrder;
import com.odoo.core.orm.provider.BaseModelProvider;

/**
 * Created by lavrentievd on 15.12.2017.
 */

public class SaleProvider extends BaseModelProvider {
    public static final String TAG = SaleProvider.class.getSimpleName();

    @Override
    public String authority() {
        return SaleOrder.AUTHORITY;
    }
}

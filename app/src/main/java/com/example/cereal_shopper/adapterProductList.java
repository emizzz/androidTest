package com.example.cereal_shopper;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;


public class adapterProductList extends ArrayAdapter<DbProduct> {
    private Context mContext;
    private String action;
    private List<DbProduct> productList = new ArrayList<>();
    DbProduct currentProduct;
    public adapterProductList(@NonNull Context context, @LayoutRes List<DbProduct> list, String _action) {
        super(context, 0 , list);
        mContext = context;
        productList = list;
        action = _action;   // autocompile || click_in_shopping_list || click_in_pantry_list

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        final int _position = position;

        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.layout_product_list_item, parent,false);

        //--------------------------------------------populate product's item------------------------------------------
        currentProduct = productList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.product_item_name);
        TextView description = (TextView) listItem.findViewById(R.id.product_item_description);

        name.setText(currentProduct.getName() + currentProduct.getId());
        description.setText(currentProduct.getNotes());



        //--------------------------------------------select product------------------------------------------------
        /*
        * ONCLICK:
        *   - if the p. is in the shopping list => recapListItem
        *   - if the p. is in the pantry list => open the dialog
        *   - if the p. is in the favorites => autocompile the form
        * */
        LinearLayout select_group = (LinearLayout) listItem.findViewById(R.id.product_list_item);

        select_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(action.equals("autocompile")){
                    Intent intent=new Intent(getContext(), AddToShoppingList.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("autocompile_fields", currentProduct.getId());
                    v.getContext().startActivity(intent);
                }
                else if(action.equals("click_in_shopping_list")){
                    Intent intent=new Intent(getContext(), RecapItem.class);
                    v.getContext().startActivity(intent);
                }
                else if(action.equals("click_in_pantry_list")){
                    //open the dialog
                }

            }
        });


        return listItem;
    }

}
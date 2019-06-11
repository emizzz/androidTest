package com.example.cereal_shopper;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/*adapter to the list of products that the user can add to or delete from the two different lists
 *it implements  buttons in each element of the list
**/
public class adapterProductList extends ArrayAdapter<DbProduct> {
    DatabaseHelper db;
    private Context mContext;
    private String action;
    private adapterProductList _this;
    private List<DbProduct> productList = new ArrayList<>();
    public adapterProductList(@NonNull Context context, @LayoutRes List<DbProduct> list, String _action) {
        super(context, 0 , list);
        mContext = context;
        productList = list;
        _this = this;
        action = _action;   // click_in_favorites || click_in_shopping_list || click_in_pantry_list
        db = new DatabaseHelper(mContext);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        final int _position = position;

        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.layout_product_list_item, parent,false);

        //--------------------------------------------populate product's item------------------------------------------
        final DbProduct currentProduct = productList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.product_item_name);
        TextView description = (TextView) listItem.findViewById(R.id.product_item_description);

        name.setText(currentProduct.getName());
        description.setText(currentProduct.getNotes());

        //---------------------------------------examples of icon-------------------------------

        ImageView image = listItem.findViewById(R.id.product_item_image);
        if(currentProduct.getName().equals(mContext.getString(R.string.esempio1))) image.setImageResource(R.drawable.pane);
        else{if(currentProduct.getName().equals(mContext.getString(R.string.esempio2))) image.setImageResource(R.drawable.latte);
        else{if(currentProduct.getName().equals(mContext.getString(R.string.esempio3))) image.setImageResource(R.drawable.pasta);
        else{if(currentProduct.getName().equals(mContext.getString(R.string.esempio4))) image.setImageResource(R.drawable.grana);
        else image.setImageResource(android.R.drawable.ic_dialog_info);}}}



        //--------------------------------------------select product------------------------------------------------

        LinearLayout select_group = (LinearLayout) listItem.findViewById(R.id.product_list_item);

        select_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /*
                * if the user click on a favorite element (in "add to shopping list")
                * */
                if(action.equals("click_in_favorites")){
                    Intent intent=new Intent(getContext(), AddToShoppingList.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("autocompile_fields", currentProduct.getId());
                    v.getContext().startActivity(intent);
                }
                else{
                    Intent intent=new Intent(getContext(), RecapItem.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("product_id", currentProduct.getId());
                    intent.putExtra("from", action);
                    v.getContext().startActivity(intent);
                }





            }
        });

        //--------------------------------------------icon click------------------------------------------------
        ImageButton icon = (ImageButton) listItem.findViewById(R.id.product_item_icon1);

        if(!currentProduct.getType().equals("shopping_list")){
            icon.setImageResource(R.drawable.ic_baseline_delete_24px);
        }


        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View _v = v;
                /*
                 * if the user click on the icon in the shopping list
                 * */
                if(action.equals("click_in_shopping_list")){
                    Intent intent=new Intent(getContext(), AddToPantry.class);
                    intent.putExtra("autocompile_fields", currentProduct.getId());
                    //TODO
                    intent.putExtra("custom_title", "Aggiungi alla dispensa");
                    v.getContext().startActivity(intent);
                }

                /*
                 * if the user click on the icon in the pantry list
                 * */
                if(action.equals("click_in_pantry_list")){

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:   //delete

                                    db.deleteProduct(currentProduct.getId());
                                    ((Lists) mContext).recreate();


                                case DialogInterface.BUTTON_NEGATIVE:   //add to shopping list

                                    try{

                                        //delete the changing info
                                        currentProduct.setType("shopping_list");
                                        currentProduct.setQuantity(1);
                                        currentProduct.setExpiry(-1);
                                        currentProduct.setNotes("");

                                        db.updateProduct(currentProduct);
                                        ((Lists) mContext).recreate();

                                    }
                                    catch (Exception e){
                                        Toast.makeText(getContext(), "Errore interno",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage(mContext.getString(R.string.aggiunta_lista_spesa)).setPositiveButton("Elimina", dialogClickListener)
                            .setNegativeButton("Riaggiungi", dialogClickListener).show();

                }


            }
        });

        return listItem;
    }

}
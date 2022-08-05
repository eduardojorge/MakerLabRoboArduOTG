package com.minimakerlabrobo;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


class CustomAdapter implements ListAdapter {
        ArrayList<SubjectData> arrayList;
        Context context;
public CustomAdapter(Context context, ArrayList<SubjectData> arrayList) {
        this.arrayList=arrayList;
        this.context=context;
        }

@Override
public boolean areAllItemsEnabled() {
        return false;
        }

@Override
public boolean isEnabled(int position) {
        return true;
        }

@Override
public void registerDataSetObserver(DataSetObserver observer) {

        }

@Override
public void unregisterDataSetObserver(DataSetObserver observer) {

        }

@Override
public int getCount() {
        return arrayList.size();
        }

@Override
public Object getItem(int position) {
        return position;
        }

@Override
public long getItemId(int position) {
        return position;
        }

@Override
public boolean hasStableIds() {
        return false;
        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {
        SubjectData subjectData=arrayList.get(position);
        if(convertView==null){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView=layoutInflater.inflate(R.layout.list_row, null);
        convertView.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {

        }
        });
        TextView tittle=convertView.findViewById(R.id.title);
        ImageView imag=convertView.findViewById(R.id.list_image);
        TextView  comando=convertView.findViewById(R.id.textComando);
        tittle.setText(subjectData.SubjectName);
        tittle.setTag(subjectData.SubjectName);
        comando.setText(subjectData.comando);
       // Picasso.with(context)
       // .load(subjectData.Image)
       // .into(imag);
                tittle.setOnClickListener(new View.OnClickListener()
                {
                        @Override
                        public void onClick(View v)
                        {


                                Intent iuploadHexActivity = new Intent(context,UploadHexActivity.class);

                                iuploadHexActivity.putExtra("file",(String) v.getTag());

                                context.startActivity(iuploadHexActivity);
                                //assign check-box state to the corresponding object in list.
                              //  CheckBox checkbox = (CheckBox) v;
                                //rowDataList.get(position).setChecked(checkbox.isChecked());
                                //Toast.makeText(activity, "CheckBox from row " + position + " was checked", Toast.LENGTH_LONG).show();
                        }
                });

        }



        return convertView;
        }

@Override
public int getItemViewType(int position) {
        return position;
        }

@Override
public int getViewTypeCount() {
        return arrayList.size();
        }


@Override
public boolean isEmpty() {
        return false;
        }
        }

class SubjectData {
    String SubjectName;
    String comando;
    String Link;
    String Image;
    public SubjectData(String subjectName, String link, String image, String comando) {
        this.SubjectName = subjectName;
        this.Link = link;
        this.Image = image;
        this.comando = comando;
    }
}


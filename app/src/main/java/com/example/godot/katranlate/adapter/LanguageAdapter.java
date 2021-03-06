package com.example.godot.katranlate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.godot.katranlate.R;
import com.example.godot.katranlate.domain.models.Language;

import java.util.List;

/**
 * Created by sergi on 09/01/16.
 */
public class LanguageAdapter extends BaseAdapter {
    private Context context;
    private List<Language> languages;

    public LanguageAdapter(Context context, List<Language> languages) {
        this.context = context;
        this.languages = languages;
    }

    @Override
    public int getCount() {
        return languages.size();
    }

    @Override
    public Object getItem(int position) {
        return languages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.spinner_item, null);

        TextView textView = (TextView) view.findViewById(R.id.language_name);
        textView.setText(languages.get(position).getName());

        return view;
    }
}

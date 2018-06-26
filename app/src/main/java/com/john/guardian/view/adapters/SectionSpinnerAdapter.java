package com.john.guardian.view.adapters;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.john.guardian.R;
import com.john.guardian.databinding.SectionSpinnerItemBinding;
import com.john.guardian.db.entity.GuardianSection;
import java.util.List;


public class SectionSpinnerAdapter extends BaseAdapter
{
    private SectionSpinnerItemBinding binding;
    private List<? extends GuardianSection> sections;

    public SectionSpinnerAdapter()
    {
    }

    public void setSections(final List<? extends GuardianSection> sections)
    {
        this.sections = sections;
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        if(sections == null)
            return 0;

        return sections.size();
    }

    @Override
    public Object getItem(int position)
    {
        if(sections == null)
            return null;

        return sections.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.section_spinner_item, parent, false);
        View v = binding.getRoot();
        SectionViewHolder holder = new SectionViewHolder(binding);
        v.setTag(holder);
        holder.binding.setSection(sections.get(position));
        holder.binding.executePendingBindings();


        return v;
    }

    static class SectionViewHolder
    {
        SectionSpinnerItemBinding binding;

        public SectionViewHolder(SectionSpinnerItemBinding binding)
        {
            this.binding = binding;
        }

    }
}

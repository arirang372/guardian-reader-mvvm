package com.john.guardian.view.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import com.john.guardian.R;
import com.john.guardian.databinding.ActivityMainBinding;
import com.john.guardian.db.entity.GuardianSection;
import com.john.guardian.view.adapters.SectionSpinnerAdapter;
import com.john.guardian.view.callbacks.SectionClickCallback;
import com.john.guardian.viewmodel.SectionListViewModel;
import java.util.List;

public class MainActivity extends AppCompatActivity
{    ActivityMainBinding binding;

    private SectionListViewModel sectionListViewModel;
    private SectionSpinnerAdapter sectionSpinnerAdapter;
    private me.zhanghai.android.materialprogressbar.MaterialProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        sectionListViewModel = ViewModelProviders.of(this).get(SectionListViewModel.class);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        progressBar = binding.getRoot().findViewById(R.id.progressbar);

        setupToolBar();
        subscribeUi(sectionListViewModel);
        if(savedInstanceState == null)
        {

        }
    }

    private void subscribeUi(SectionListViewModel viewModel) {
        // Update the list when the data changes
        viewModel.getSections().observe(this, new Observer<List<GuardianSection>>() {
            @Override
            public void onChanged(@Nullable List<GuardianSection> sections)
            {
                if (sections != null)
                {
                    binding.setIsLoading(false);
                    progressBar.setVisibility(View.INVISIBLE);
                    sectionSpinnerAdapter.setSections(sections);
                }
                else {
                    binding.setIsLoading(true);
                    progressBar.setVisibility(View.VISIBLE);
                }

                binding.executePendingBindings();
            }
        });
    }

    private SectionClickCallback sectionClickCallback = new SectionClickCallback() {

        @Override
        public void onClick(GuardianSection section)
        {

        }
    };

    public void setupToolBar()
    {
        sectionSpinnerAdapter = new SectionSpinnerAdapter(sectionClickCallback);
        binding.spSections.setAdapter(sectionSpinnerAdapter);
        binding.spSections.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                //presenter.titleSpinnerSelectionSelected(sections.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


}

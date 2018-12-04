package com.john.guardian.view.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import com.john.guardian.R;
import com.john.guardian.databinding.ActivityNewsContentBinding;
import com.john.guardian.db.entity.GuardianContent;
import com.john.guardian.viewmodel.ContentViewModel;

public class NewsContentActivity extends AppCompatActivity
{
    ActivityNewsContentBinding binding;

    private int contentId;

    private ProgressBar progressBar;
    private WebView wv_main;

    private ContentViewModel contentViewModel;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news_content);
        progressBar = binding.getRoot().findViewById(R.id.progressbar);
        wv_main = binding.getRoot().findViewById(R.id.wv_main);
        toolbar = binding.getRoot().findViewById(R.id.toolbar);
        setSupportActionBar(binding.toolbar);

        progressBar.setVisibility(View.VISIBLE);
        showProgress();
        if(getIntent() != null)
        {
            contentId = getIntent().getIntExtra("content_id", 0);
            ContentViewModel.Factory factory = new ContentViewModel.Factory(getApplication(), contentId);
            contentViewModel = ViewModelProviders.of(this, factory).get(ContentViewModel.class);
            subscribeToViewModel(contentViewModel);
        }

    }

    private void subscribeToViewModel(final ContentViewModel viewModel)
    {
        viewModel.getObservableContent().observe(this, new Observer<GuardianContent>() {
            @Override
            public void onChanged(@Nullable GuardianContent content)
            {
                if(content != null)
                {
                    toolbar.setTitle(content.getWebTitle());
                    wv_main.getSettings().setJavaScriptEnabled(true);
                    wv_main.loadUrl(content.getWebUrl());
                }
            }
        });

        hideProgress();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }


    public void showProgress()
    {
        progressBar.setAlpha(1.0f);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress()
    {
            if(progressBar != null)
            {
                if (progressBar.getVisibility() != View.GONE) {
                    progressBar.animate().alpha(0f).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationCancel(Animator animation) {
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }

    }



}

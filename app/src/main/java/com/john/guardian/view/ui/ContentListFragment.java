package com.john.guardian.view.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.john.guardian.R;
import com.john.guardian.databinding.ListFragmentBinding;
import com.john.guardian.db.entity.GuardianContent;
import com.john.guardian.view.adapters.ContentRecyclerViewAdapter;
import com.john.guardian.view.callbacks.ContentClickCallback;
import com.john.guardian.viewmodel.ContentListViewModel;

import java.util.List;

public class ContentListFragment extends Fragment
{
    private ContentRecyclerViewAdapter adapter;
    private ListFragmentBinding binding;
    private ContentListViewModel contentListViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
         binding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false);
         adapter = new ContentRecyclerViewAdapter(contentClickCallback);
         binding.contentList.setAdapter(adapter);

         return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        ContentListViewModel.Factory factory = new ContentListViewModel.Factory(
                getActivity().getApplication(), getArguments().getString("section_id"));

        contentListViewModel= ViewModelProviders.of(this, factory)
                .get(ContentListViewModel.class);

        subscribeUi(contentListViewModel);

        binding.refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                binding.refreshView.setRefreshing(false);

            }
        });
    }

    private void subscribeUi(ContentListViewModel viewModel)
    {
        viewModel.getObservableContents().observe(this, new Observer<List<GuardianContent>>() {
            @Override
            public void onChanged(@Nullable List<GuardianContent> contents) {
                if(contents != null)
                {
                    binding.setIsLoadingBarOn(false);
                    binding.contentList.setVisibility(View.VISIBLE);
                    adapter.setContents(contents);
                }
                else
                {
                    binding.setIsLoadingBarOn(true);
                    binding.loadingTv.setVisibility(View.VISIBLE);
                    binding.contentList.setVisibility(View.GONE);
                }

                binding.executePendingBindings();
            }
        });
    }

    private final ContentClickCallback contentClickCallback = new ContentClickCallback()
    {
        @Override
        public void onClick(GuardianContent content)
        {
            ((MainActivity)getActivity()).showGuardianContent(content.getId());
        }
    };


    public static ContentListFragment newInstance(String sectionId) {
        ContentListFragment fragment = new ContentListFragment();
        Bundle args = new Bundle();
        args.putString("section_id", sectionId);
        fragment.setArguments(args);
        return fragment;
    }

}

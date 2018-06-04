package com.niko.likechecker.ui.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.niko.likechecker.R
import com.niko.likechecker.extensions.searchObservable
import com.niko.likechecker.model.fastAdapterItems.FriendItem
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.dialog_fragment_friend.*
import org.greenrobot.eventbus.EventBus

class DialogFriendFragment : DialogFragment() {

    private lateinit var itemAdapter: ItemAdapter<FriendItem>
    private lateinit var fastAdapter: FastAdapter<FriendItem>
    private lateinit var listFriends: List<FriendItem>
    private lateinit var searchObservable: Disposable

    companion object {
        fun newInstance(list: List<FriendItem>): DialogFriendFragment {
            val dialogFriendFragment = DialogFriendFragment()
            dialogFriendFragment.listFriends = list
            return dialogFriendFragment
        }
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.70).toInt()
        dialog.window.setLayout(width, height)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_fragment_friend, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemAdapter = ItemAdapter()

        itemAdapter.itemFilter.withFilterPredicate { item, constraint -> item.friend.name.toLowerCase().startsWith(constraint.toString().toLowerCase()) }

        fastAdapter = FastAdapter.with(itemAdapter)

        list.layoutManager = LinearLayoutManager(context)

        itemAdapter.add(listFriends)

        list.adapter = fastAdapter

        searchObservable = editSearch.searchObservable().subscribe(itemAdapter::filter)

        fastAdapter.withOnClickListener { _, _, item, _ ->
            EventBus.getDefault().post(item.friend)
            dismiss()
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchObservable.dispose()
    }

}

package com.msk.simpletodo.presentation.viewModel.todo

import android.graphics.Canvas
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.ACTION_STATE_SWIPE
import androidx.recyclerview.widget.RecyclerView
import com.msk.simpletodo.R
import java.lang.Float.max
import java.lang.Float.min

class SwipeHelperCallback :
    ItemTouchHelper.Callback() {

    private var currentPosition: Int? = null
    private var previousPosition: Int? = null
    private var currentDx = 0f
    private var clamp = 0f

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val view = getView(viewHolder)
        clamp = view.width.toFloat() / 10 * 3.5f
        return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
    }

    /**
     * Related Drag and Drop option
     * so std don't use this method
     */
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    }


    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        currentDx = 0f
        getDefaultUIUtil().clearView(getView(viewHolder))
        previousPosition = viewHolder.adapterPosition
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        viewHolder?.let {
            currentPosition = viewHolder.adapterPosition
            getDefaultUIUtil().onSelected(getView(it))
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ACTION_STATE_SWIPE) {
            val view = getView(viewHolder)
            val isClamped = getTag(viewHolder)
            val x = clampViewPositionHorizontal(view, dX, isClamped, isCurrentlyActive)

            currentDx = x
            getDefaultUIUtil().onDraw(c, recyclerView, view, x, dY, actionState, isCurrentlyActive)
            view.animate().translationX(x).setDuration(200).withStartAction {
            }.start()
        }
    }

    private fun getView(viewHolder: RecyclerView.ViewHolder): View {
        return (viewHolder as TodoListAdapter.TodoListViewHolder).itemView.findViewById(R.id.swipe_view)
    }

    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return defaultValue * 20
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        val isClamped = getTag(viewHolder)
        val deleteButton =
            (viewHolder as TodoListAdapter.TodoListViewHolder).itemView.findViewById<AppCompatButton>(
                R.id.taskDeleteButton
            )
        val editButton =
            viewHolder.binding.taskEditButton
        if (currentDx <= -clamp) {
            deleteButton.isEnabled = true
            editButton.isEnabled = true
        } else {
            deleteButton.isEnabled = false
            editButton.isEnabled = false
        }
        setTag(viewHolder, !isClamped && currentDx <= -clamp)
        return 2f
    }

    private fun getTag(viewHolder: RecyclerView.ViewHolder): Boolean {
        return viewHolder.itemView.tag as? Boolean ?: false
    }

    private fun setTag(viewHolder: RecyclerView.ViewHolder, isClamped: Boolean) {
        viewHolder.itemView.tag = isClamped
    }

    fun setClamp(clamp: Float) {
        this.clamp = clamp
    }

    private fun clampViewPositionHorizontal(
        view: View,
        dX: Float,
        isClamped: Boolean,
        isCurrentlyActive: Boolean
    ): Float {
        // View의 가로 길이의 절반까지만 swipe 되도록
        val maxSwipe: Float = -view.width.toFloat() / 2.8f
        // RIGHT 방향으로 swipe 막기
        val right: Float = 0f

        val x = if (isClamped) {
            // View가 고정되었을 때 swipe되는 영역 제한
            if (isCurrentlyActive) dX - clamp else -clamp
        } else {
            dX
        }
        Log.d("TAG", "clampViewPositionHorizontal: ${min(max(maxSwipe, x), right)}")
        return min(max(maxSwipe, x), right)
    }

    fun removePreviousClamp(recyclerView: RecyclerView) {
        previousPosition?.let {
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(it) ?: return
            getView(viewHolder).animate().translationX(0f).setDuration(200).withEndAction {
                val deleteButton =
                    (viewHolder as TodoListAdapter.TodoListViewHolder).itemView.findViewById<AppCompatButton>(
                        R.id.taskDeleteButton
                    )
                val editButton =
                    viewHolder.binding.taskEditButton
                deleteButton.isEnabled = false
                editButton.isEnabled = false
            }.start()

            setTag(viewHolder, false)
            previousPosition = null
        }
    }
}
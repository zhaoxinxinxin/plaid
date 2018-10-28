/*
 * Copyright 2018 Google, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.plaidapp.designernews.ui.story

import android.animation.Animator
import android.animation.AnimatorSet
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import io.plaidapp.core.designernews.domain.model.Comment
import io.plaidapp.core.util.AnimUtils.getFastOutSlowInInterpolator
import io.plaidapp.designernews.R

/**
 * View holder for a Designer News comment reply.
 * TODO move more CommentReply related actions here
 */
internal class CommentReplyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val commentVotes: Button = itemView.findViewById(R.id.comment_votes)
    private val replyLabel: TextInputLayout = itemView.findViewById(R.id.comment_reply_label)
    val commentReply: EditText = itemView.findViewById(R.id.comment_reply)
    val postReply: ImageButton = itemView.findViewById(R.id.post_reply)

    fun bindCommentReply(comment: Comment) {
        commentVotes.text = comment.upvotesCount.toString()
        commentVotes.isActivated = comment.upvoted != null && comment.upvoted
    }

    fun createCommentReplyFocusAnimator(): Animator {
        val interpolator = getFastOutSlowInInterpolator(itemView.context)

        commentVotes.animate()
            .translationX((-commentVotes.width).toFloat())
            .alpha(0f)
            .setDuration(200L)
            .interpolator = interpolator

        replyLabel.animate()
            .translationX((-commentVotes.width).toFloat())
            .setDuration(200L)
            .interpolator = interpolator

        postReply.visibility = View.VISIBLE
        postReply.alpha = 0f
        postReply.animate()
            .alpha(1f)
            .setDuration(200L)
            .interpolator = interpolator

        return AnimatorSet().apply {
            doOnStart {
                itemView.setHasTransientState(true)
            }
            doOnEnd {
                itemView.setHasTransientState(false)
            }
        }
    }

    fun createCommentReplyFocusLossAnimator(): Animator {
        val interpolator = getFastOutSlowInInterpolator(itemView.context)

        commentVotes.animate()
            .translationX(0f)
            .alpha(1f)
            .setDuration(200L)
            .interpolator = interpolator

        replyLabel.animate()
            .translationX(0f)
            .setDuration(200L)
            .interpolator = interpolator

        postReply.animate()
            .alpha(0f)
            .setDuration(200L)
            .interpolator = interpolator

        return AnimatorSet().apply {
            doOnStart {
                itemView.setHasTransientState(true)
            }
            doOnEnd {
                postReply.visibility = View.INVISIBLE
                itemView.setHasTransientState(true)
            }
        }
    }
}

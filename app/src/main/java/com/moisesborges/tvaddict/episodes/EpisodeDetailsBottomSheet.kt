package com.moisesborges.tvaddict.episodes

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.moisesborges.tvaddict.R
import com.moisesborges.tvaddict.models.Episode
import com.moisesborges.tvaddict.utils.HtmlUtils
import kotlinx.android.synthetic.main.bottom_sheet_episode_details.*

/**
 * Created by moises.anjos on 13/07/2017.
 */
class EpisodeDetailsBottomSheet: BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.bottom_sheet_episode_details, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val episode = arguments.getParcelable<Episode>(EPISODE_ARG)
        episode_summary_text_view.text = HtmlUtils.extractFromHtml(episode.summary)
    }

    companion object {

        internal val EPISODE_ARG = "${EpisodeDetailsBottomSheet::class.java.simpleName}.episode"

        @JvmStatic
        fun show(episode: Episode, manager: FragmentManager) {
            val bottomSheet = EpisodeDetailsBottomSheet()
            val args = Bundle()
            args.putParcelable(EPISODE_ARG, episode)
            bottomSheet.arguments = args
            bottomSheet.show(manager, "Episode Details Bottom Sheet")
        }
    }
}
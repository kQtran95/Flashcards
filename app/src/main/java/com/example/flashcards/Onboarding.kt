package com.example.flashcards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.leanback.app.OnboardingSupportFragment

class Onboarding : OnboardingSupportFragment() {
	/**
	 * Returns the title of the given page.
	 *
	 * @param pageIndex The page index.
	 *
	 * @return The title of the page.
	 */
	override fun getPageTitle(pageIndex: Int): CharSequence {
		if (pageIndex == 0)
			return "TUTORIAL"
		return "PAGE TITLE"
	}
	
	/**
	 * Returns the description of the given page.
	 *
	 * @param pageIndex The page index.
	 *
	 * @return The description of the page.
	 */
	override fun getPageDescription(pageIndex: Int): CharSequence {
		if (pageIndex == 0)
			return "IT'S A TUTORIAL PAGE"
		return "DESCRIPTION"
	}
	
	/**
	 * Called to have the inherited class create foreground view. This is optional and the fragment
	 * which doesn't need the foreground view can return `null`. This is called inside
	 * [.onCreateView].
	 *
	 *
	 * This foreground view would have the highest z-order.
	 *
	 * @param inflater The LayoutInflater object that can be used to inflate the views,
	 * @param container The parent view that the additional views are attached to.The fragment
	 * should not add the view by itself.
	 *
	 * @return The foreground view for the onboarding screen, or `null`.
	 */
	override fun onCreateForegroundView(inflater: LayoutInflater?, container: ViewGroup?): View? {
		TODO("Not yet implemented")
	}
	
	/**
	 * Called to have the inherited class create background view. This is optional and the fragment
	 * which doesn't have the background view can return `null`. This is called inside
	 * [.onCreateView].
	 *
	 * @param inflater The LayoutInflater object that can be used to inflate the views,
	 * @param container The parent view that the additional views are attached to.The fragment
	 * should not add the view by itself.
	 *
	 * @return The background view for the onboarding screen, or `null`.
	 */
	override fun onCreateBackgroundView(inflater: LayoutInflater?, container: ViewGroup?): View? {
		TODO("Not yet implemented")
	}
	
	/**
	 * Returns the page count.
	 *
	 * @return The page count.
	 */
	override fun getPageCount(): Int {
		TODO("Not yet implemented")
	}
	
	/**
	 * Called to have the inherited class create content view. This is optional and the fragment
	 * which doesn't have the content view can return `null`. This is called inside
	 * [.onCreateView].
	 *
	 *
	 * The content view would be located at the center of the screen.
	 *
	 * @param inflater The LayoutInflater object that can be used to inflate the views,
	 * @param container The parent view that the additional views are attached to.The fragment
	 * should not add the view by itself.
	 *
	 * @return The content view for the onboarding screen, or `null`.
	 */
	override fun onCreateContentView(inflater: LayoutInflater?, container: ViewGroup?): View? {
		TODO("Not yet implemented")
	}
	
	
}
/*
 * Copyright (C) 2015 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.kuma.library.twitterfavo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageButton;

/**
 * Display on/off states (ie: Favorite or Retweet action buttons) as an {@link ImageButton}.
 *
 *
 * By default the button will be toggled when clicked. This behaviour can be prevented by setting
 * the {@code twitter:toggleOnClick} attribute to false.
 *
 */
public class TwitterFavoImageButton extends ImageButton {
    private static final int[] STATE_TOGGLED_ON = {R.attr.state_toggled_on};

    boolean isToggledOn;
    final boolean toggleOnClick;

    public TwitterFavoImageButton(Context context) {
        this(context, null);
    }

    public TwitterFavoImageButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TwitterFavoImageButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = null;
        try {
            a = context.getTheme().obtainStyledAttributes(attrs,
                    R.styleable.TwitterFavoImageButton, defStyle, 0);

            toggleOnClick = a.getBoolean(R.styleable.TwitterFavoImageButton_toggleOnClick, true);

            setImageResource(R.drawable.tw__like_action);

            setToggledOn(false);
        } finally {
            if (a != null) {
                a.recycle();
            }
        }
    }

    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 2);
        if (isToggledOn) {
            mergeDrawableStates(drawableState, STATE_TOGGLED_ON);
        }
        return drawableState;
    }

    @Override
    public boolean performClick() {
        if (toggleOnClick) {
            toggle();
        }
        return super.performClick();
    }

    public void setToggledOn(boolean isToggledOn) {
        this.isToggledOn = isToggledOn;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            refreshDrawableState();
        } else {
            if (isToggledOn) {
                setImageResource(R.drawable.animation_list_favo);
                AnimationDrawable favoAnimation = (AnimationDrawable) getDrawable();
                favoAnimation.start();
            } else {
                setImageResource(R.drawable.tw__like_action);
            }
        }
    }

    public void toggle() {
        setToggledOn(!isToggledOn);
    }

    public boolean isToggledOn() {
        return isToggledOn;
    }
}

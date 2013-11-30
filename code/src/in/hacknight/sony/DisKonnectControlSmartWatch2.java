/*
Copyright (c) 2011, Sony Ericsson Mobile Communications AB
Copyright (c) 2011-2013, Sony Mobile Communications AB

 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this
 list of conditions and the following disclaimer.

 * Redistributions in binary form must reproduce the above copyright notice,
 this list of conditions and the following disclaimer in the documentation
 and/or other materials provided with the distribution.

 * Neither the name of the Sony Ericsson Mobile Communications AB / Sony Mobile
 Communications AB nor the names of its contributors may be used to endorse or promote
 products derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package in.hacknight.sony;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.sonyericsson.extras.liveware.aef.control.Control;
import com.sonyericsson.extras.liveware.extension.util.ExtensionUtils;
import com.sonyericsson.extras.liveware.extension.util.control.ControlExtension;
import com.sonyericsson.extras.liveware.extension.util.control.ControlListItem;
import com.sonyericsson.extras.liveware.extension.util.control.ControlObjectClickEvent;
import com.sonyericsson.extras.liveware.extension.util.control.ControlTouchEvent;
import com.sonyericsson.extras.liveware.extension.util.control.ControlView;
import com.sonyericsson.extras.liveware.extension.util.control.ControlView.OnClickListener;
import com.sonyericsson.extras.liveware.extension.util.control.ControlViewGroup;
import in.hacknight.diskonnect.R;

/**
 * The sample control for SmartWatch handles the control on the accessory. This
 * class exists in one instance for every supported host application that we
 * have registered to
 */
class DisKonnectControlSmartWatch2 extends ControlExtension {

    private static final int MENU_ITEM_0 = 0;
    private static final int MENU_ITEM_1 = 1;
    private static final int MENU_ITEM_2 = 2;
    private static final int MENU_ITEM_3 = 3;
    private static final int MENU_ITEM_4 = 4;
    private static final int MENU_ITEM_5 = 5;

    private Handler mHandler;
    
    private ControlViewGroup mLayout = null;

    private boolean mTextMenu = false;
    private boolean mDisKonnected = false;
    Bundle[] mMenuItemsText = new Bundle[3];
    Bundle[] mMenuItemsIcons = new Bundle[3];

    /**
     * Create sample control.
     *
     * @param hostAppPackageName Package name of host application.
     * @param context The context.
     * @param handler The handler to use
     */
    DisKonnectControlSmartWatch2(final String hostAppPackageName, final Context context,
            Handler handler) {
        super(context, hostAppPackageName);
        if (handler == null) {
            throw new IllegalArgumentException("handler == null");
        }
        mHandler = handler;
        setupClickables(context);
        initializeMenus();
    }

    private void initializeMenus() {
        mMenuItemsText[0] = new Bundle();
        mMenuItemsText[0].putInt(Control.Intents.EXTRA_MENU_ITEM_ID, MENU_ITEM_0);
        mMenuItemsText[0].putString(Control.Intents.EXTRA_MENU_ITEM_TEXT, "Item 1");
        mMenuItemsText[1] = new Bundle();
        mMenuItemsText[1].putInt(Control.Intents.EXTRA_MENU_ITEM_ID, MENU_ITEM_1);
        mMenuItemsText[1].putString(Control.Intents.EXTRA_MENU_ITEM_TEXT, "Item 2");
        mMenuItemsText[2] = new Bundle();
        mMenuItemsText[2].putInt(Control.Intents.EXTRA_MENU_ITEM_ID, MENU_ITEM_2);
        mMenuItemsText[2].putString(Control.Intents.EXTRA_MENU_ITEM_TEXT, "Item 3");

        mMenuItemsIcons[0] = new Bundle();
        mMenuItemsIcons[0].putInt(Control.Intents.EXTRA_MENU_ITEM_ID, MENU_ITEM_3);
        mMenuItemsIcons[0].putString(Control.Intents.EXTRA_MENU_ITEM_ICON,
                ExtensionUtils.getUriString(mContext, R.drawable.actions_call));
        mMenuItemsIcons[1] = new Bundle();
        mMenuItemsIcons[1].putInt(Control.Intents.EXTRA_MENU_ITEM_ID, MENU_ITEM_4);
        mMenuItemsIcons[1].putString(Control.Intents.EXTRA_MENU_ITEM_ICON,
                ExtensionUtils.getUriString(mContext, R.drawable.actions_reply));
        mMenuItemsIcons[2] = new Bundle();
        mMenuItemsIcons[2].putInt(Control.Intents.EXTRA_MENU_ITEM_ID, MENU_ITEM_5);
        mMenuItemsIcons[2].putString(Control.Intents.EXTRA_MENU_ITEM_ICON,
                ExtensionUtils.getUriString(mContext, R.drawable.actions_view_in_phone));
    }

    /**
     * Get supported control width.
     *
     * @param context The context.
     * @return the width.
     */
    public static int getSupportedControlWidth(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.smart_watch_2_control_width);
    }

    /**
     * Get supported control height.
     *
     * @param context The context.
     * @return the height.
     */
    public static int getSupportedControlHeight(Context context) {
        return context.getResources().getDimensionPixelSize(R.dimen.smart_watch_2_control_height);
    }

    @Override
    public void onDestroy() {
        Log.d(DisKonnectExtensionService.LOG_TAG, "SampleControlSmartWatch onDestroy");
        //stopAnimation();
        mHandler = null;
    };

    @Override
    public void onStart() {
        // Nothing to do. Animation is handled in onResume.
    }

    @Override
    public void onStop() {
        // Nothing to do. Animation is handled in onPause.
    }

    @Override
    public void onResume() {
        Log.d(DisKonnectExtensionService.LOG_TAG, "Starting animation");
        /*
        Bundle b1 = new Bundle();
        b1.putInt(Control.Intents.EXTRA_LAYOUT_REFERENCE, R.id.sample_control_text_1);
        b1.putString(Control.Intents.EXTRA_TEXT, "1");

        Bundle b2 = new Bundle();
        b2.putInt(Control.Intents.EXTRA_LAYOUT_REFERENCE, R.id.sample_control_text_2);
        b2.putString(Control.Intents.EXTRA_TEXT, "2");

        Bundle b3 = new Bundle();
        b3.putInt(Control.Intents.EXTRA_LAYOUT_REFERENCE, R.id.sample_control_text_3);
        b3.putString(Control.Intents.EXTRA_TEXT, "3");

        Bundle b4 = new Bundle();
        b4.putInt(Control.Intents.EXTRA_LAYOUT_REFERENCE, R.id.sample_control_text_4);
        b4.putString(Control.Intents.EXTRA_TEXT, "4");

        Bundle[] data = new Bundle[4];

        data[0] = b1;
        data[1] = b2;
        data[2] = b3;
        data[3] = b4;

        showLayout(R.layout.sample_control_2, data);
	
        startAnimation();
        */
        if (mDisKonnected)
    		sendImage(R.id.button_watch, R.drawable.btn_connect);
    	else
    		sendImage(R.id.button_watch, R.drawable.btn_diskonnect);
        showLayout(R.layout.watch_control_view	,null);
    }

    @Override
    public void onPause() {
        Log.d(DisKonnectExtensionService.LOG_TAG, "Stopping animation");
        //stopAnimation();
    }

  


    @Override
    public void onTouch(final ControlTouchEvent event) {
        Log.d(DisKonnectExtensionService.LOG_TAG, "onTouch() " + event.getAction());
        if (event.getAction() == Control.Intents.TOUCH_ACTION_RELEASE) {
            Log.d(DisKonnectExtensionService.LOG_TAG, "Toggling animation");
            //toggleAnimation();
        }
    }

    @Override
    public void onObjectClick(final ControlObjectClickEvent event) {
        Log.d(DisKonnectExtensionService.LOG_TAG, "onObjectClick() " + event.getClickType());
        if (event.getLayoutReference() != -1) {
            mLayout.onClick(event.getLayoutReference());
        }
    }

    @Override
    public void onKey(final int action, final int keyCode, final long timeStamp) {
        Log.d(DisKonnectExtensionService.LOG_TAG, "onKey()");
        if (action == Control.Intents.KEY_ACTION_RELEASE
                && keyCode == Control.KeyCodes.KEYCODE_OPTIONS) {
            toggleMenu();
        }
        else if (action == Control.Intents.KEY_ACTION_RELEASE
                && keyCode == Control.KeyCodes.KEYCODE_BACK) {
            Log.d(DisKonnectExtensionService.LOG_TAG, "onKey() - back button intercepted.");
        }
    }

    @Override
    public void onMenuItemSelected(final int menuItem) {
        Log.d(DisKonnectExtensionService.LOG_TAG, "onMenuItemSelected() - menu item " + menuItem);
        if (menuItem == MENU_ITEM_0) {
            clearDisplay();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onResume();
                }
            }, 1000);
        }
    }

    private void toggleMenu() {
        if (mTextMenu) {
            showMenu(mMenuItemsIcons);
        }
        else
        {
            showMenu(mMenuItemsText);
        }
        mTextMenu = !mTextMenu;
    }

   
    
    private String[] accs= {"for Yoga", "for Drive","for Sleep"};
    private void setupClickables(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.watch_control_view
                , null);
        mLayout = (ControlViewGroup) parseLayout(layout);
        if (mLayout != null) {
            ControlView button = mLayout.findViewById(R.id.button_watch);
            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick() {
                	if (mDisKonnected) {
                		showLayout(R.layout.watch_list_view,null);
                        sendListCount(R.id.listView, accs.length);

                	}
                		//sendImage(R.id.button_watch, R.drawable.btn_connect);
                	else
                		sendImage(R.id.button_watch, R.drawable.btn_diskonnect);
                	mDisKonnected = !mDisKonnected;
                }
            });
           
        }
    }
    

    @Override
    public void onRequestListItem(final int layoutReference, final int listItemPosition) {
        if (layoutReference != -1 && listItemPosition != -1 && layoutReference == R.id.listView) {
            ControlListItem item = createControlListItem(listItemPosition);
            if (item != null) {
                sendListItem(item);
            }
        }
    }
    protected ControlListItem createControlListItem(int position) {

        ControlListItem item = new ControlListItem();
        item.layoutReference = R.id.listView;
        item.dataXmlLayout = R.layout.watch_list_dataview;
        item.listItemPosition = position;
        // We use position as listItemId. Here we could use some other unique id
        // to reference the list data
        item.listItemId = position;
        /*
        int icon = R.drawable.thumbnail_list_item;
        // Icon data
        Bundle iconBundle = new Bundle();
        iconBundle.putInt(Control.Intents.EXTRA_LAYOUT_REFERENCE, R.id.thumbnail);
        iconBundle.putString(Control.Intents.EXTRA_DATA_URI,
                ExtensionUtils.getUriString(mContext, icon));
         */
        // Header data
        Bundle headerBundle = new Bundle();
        headerBundle.putInt(Control.Intents.EXTRA_LAYOUT_REFERENCE, R.id.title);
        headerBundle.putString(Control.Intents.EXTRA_TEXT, accs[position]);

        // Body data
        Bundle bodyBundle = new Bundle();
        bodyBundle.putInt(Control.Intents.EXTRA_LAYOUT_REFERENCE, R.id.body);
        bodyBundle.putString(Control.Intents.EXTRA_TEXT, accs[position]);

        item.layoutData = new Bundle[3];
        //item.layoutData[0] = iconBundle;
        item.layoutData[1] = headerBundle;
        item.layoutData[2] = bodyBundle;

        return item;
    }
    private class SelectToggler implements Runnable {

        private int mLayoutReference;
        private int mResourceId;

        SelectToggler(int layoutReference, int resourceId) {
            mLayoutReference = layoutReference;
            mResourceId = resourceId;
        }

        @Override
        public void run() {
            sendImage(mLayoutReference, mResourceId);
        }

    }



}

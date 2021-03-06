package net.alexandroid.shprefhelper;

import android.app.Activity;
import android.os.RemoteException;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.widget.TextView;

import net.alexandroid.shpref.ShPref;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private UiDevice mDevice;

    @Rule
    public MainActivityTestRule<MainActivity> mMainActivityActivityTestRule = new MainActivityTestRule<>(MainActivity.class);

    @Before
    public void beforeEachTestClearShPref() {
        ShPref.clear();
        mDevice = UiDevice.getInstance(getInstrumentation());
        System.out.println("=== ShPref is cleared ===");
    }

    @Test
    public void testUI() throws InterruptedException, UiObjectNotFoundException {
        Activity activity = mMainActivityActivityTestRule.getActivity();
        assertNotNull(activity.findViewById(R.id.textView));
        TextView textView = (TextView) activity.findViewById(R.id.textView);
        assertTrue(textView.isShown());
        assertEquals(textView.getText(), activity.getString(R.string.sample_app));

        UiObject tv = getUiObjectByText(R.string.sample_app);
        tv.setText("========== TEST ==========");
        waitSeconds(2);
    }

    @Test
    public void testPressBackButton() throws RemoteException, InterruptedException {
        UiDevice mDevice = UiDevice.getInstance(getInstrumentation());
        if (mDevice.isScreenOn()) {
            waitSeconds(2);
            mDevice.setOrientationLeft();
            waitSeconds(2);
            mDevice.openNotification();
            waitSeconds(2);
            mDevice.setOrientationNatural();
            waitSeconds(2);
        }
        mDevice.pressBack();
    }

    @Test
    public void testSaveAndLoad() throws UiObjectNotFoundException, InterruptedException {
        UiObject loadBtn = getUiObjectByText(R.string.load);
        UiObject saveBtn = getUiObjectByText(R.string.save);
        clickOnWidgetAndWait(loadBtn);
        clickOnWidgetAndWait(saveBtn);
        clickOnWidgetAndWait(loadBtn);
    }


    @Test
    public void testContainsRemove() throws UiObjectNotFoundException, InterruptedException {
        UiObject saveBtn = getUiObjectByText(R.string.save);
        UiObject containsBtn = getUiObjectByText(R.string.contains);
        UiObject removeBtn = getUiObjectByText(R.string.remove);

        clickOnWidgetAndWait(containsBtn);
        clickOnWidgetAndWait(saveBtn);
        clickOnWidgetAndWait(containsBtn);
        clickOnWidgetAndWait(removeBtn);
        clickOnWidgetAndWait(containsBtn);
    }


    @Test
    public void testSaveAndLoadWithEditor() throws UiObjectNotFoundException, InterruptedException {
        UiObject saveBtn = getUiObjectByText(R.string.save_with_editor);
        UiObject loadBtn = getUiObjectByText(R.string.load2);
        clickOnWidgetAndWait(loadBtn);
        clickOnWidgetAndWait(saveBtn);
        clickOnWidgetAndWait(loadBtn);
    }

    @Test
    public void testLogger() throws InterruptedException, UiObjectNotFoundException {
        UiObject testLoggerBtn = getUiObjectByText(R.string.test_logger);
        clickOnWidgetAndWait(testLoggerBtn);
    }

    @Test
    public void testSaveAndLoadList() throws InterruptedException, UiObjectNotFoundException {
        UiObject saveBtn = getUiObjectByText(R.string.save_list);
        UiObject loadBtn = getUiObjectByText(R.string.load_list);
        clickOnWidgetAndWait(loadBtn);
        clickOnWidgetAndWait(saveBtn);
        clickOnWidgetAndWait(loadBtn);
    }

    private UiObject getUiObjectByText(int stringRes) {
        Activity activity = mMainActivityActivityTestRule.getActivity();
        UiSelector selector = new UiSelector().text(activity.getString(stringRes));
        return mDevice.findObject(selector);
    }

    private void clickOnWidgetAndWait(UiObject aWidget) throws UiObjectNotFoundException, InterruptedException {
        aWidget.click();
        waitSeconds(4);
    }

    private void waitSeconds(int sec) throws InterruptedException {
        Thread.sleep(sec * 1000);
    }


}

package com.example.spotthis;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DataBaseTest {

    private AppDatabase mDatabase;

    private ArrayList<String> categories = new ArrayList<>();
    private Image IMAGE = new Image("hej jeg hedder niels", categories, "12345");
    @Before
    public void initDb() throws Exception {
        categories.add("Cat");
        categories.add("Dog");
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), AppDatabase.class).build();
    }

    @After
    public void closeDb() throws Exception {
        mDatabase.close();
    }

    @Test
    public void insertAndGetImage() {
        mDatabase.imageDAO().insert(IMAGE);

        List<Image> images = mDatabase.imageDAO().getImages();
        assertThat(images.size(), is(1));
        Image dbimage = images.get(0);
        assertEquals(dbimage.getUri(), IMAGE.getUri());
        assertEquals(dbimage.getDescription(), IMAGE.getDescription());
        assertEquals(dbimage.getCategories(), IMAGE.getCategories());
        assertEquals(dbimage.getUri(), IMAGE.getUri());
    }
}

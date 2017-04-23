package com.moisesborges.tvaddict.data;

import com.moisesborges.tvaddict.models.Episode;
import com.moisesborges.tvaddict.models.Show;
import com.moisesborges.tvaddict.rules.DBFlow;
import com.moisesborges.tvaddict.utils.MockShow;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * Created by Moisés on 22/04/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 21)
public class EpisodesDbImplTest {

    @Rule
    public final DBFlow mDbFlow = DBFlow.create();
    private Show mShow;
    private EpisodesDb mEpisodesDb;

    @Before
    public void setUp() throws Exception {
        mShow = MockShow.newMockShowInstance();
        mShow.setEmbedded(MockShow.newEmbeddedInstance());
        mEpisodesDb = new EpisodesDbImpl();
    }

    @After
    public void tearDown() throws Exception {
        SQLite.delete()
                .from(Episode.class)
                .execute();
    }

    @Test
    public void shouldSaveTheEpisode() throws Exception {
        Episode firstEpisode = mShow.getEmbedded()
                .getEpisodes()
                .get(0);

    }
}
package com.example.boredApplication.Data.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.boredApplication.Data.Models.ActivityEntity
import com.example.boredApplication.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class AppDatabaseTest: TestCase() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var dao: ActivityDao

    @Before
    override public fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()
        dao = database.getActivityDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun selectActiviitiesFromDBTest() = runBlockingTest {
        val activityToAdd1 = ActivityEntity("Learn how to use an Arduino", "education", 13, 1.1, "https://en.wikipedia.org/wiki/Arduino", "8264223", 1.5)
        val activityToAdd2 = ActivityEntity("Learn how to use an Computer", "IT", 14, 1.2, "https://en.wikipedia.org/wiki/Arduino", "85", 1.5)
        val activityToAdd3 = ActivityEntity("Learn how to use an swim", "Natation", 15, 1.3, "https://en.wikipedia.org/wiki/Arduino", "1235", 1.5)
        val dataAdded = listOf(activityToAdd1, activityToAdd2, activityToAdd3)

        dao.insertActivity(activityToAdd1)
        dao.insertActivity(activityToAdd2)
        dao.insertActivity(activityToAdd3)
        val selectActivities = dao.getAll().getOrAwaitValue()

        assertThat(selectActivities).isEqualTo(dataAdded)
    }

    @Test
    fun insertActivityToDBTest() = runBlockingTest {
        val activityToAdd1 = ActivityEntity("Learn how to use an Arduino", "education", 13, 1.1, "https://en.wikipedia.org/wiki/Arduino", "8264223", 1.5)

        dao.insertActivity(activityToAdd1)
        val selectActivities = dao.getAll().getOrAwaitValue()

        assertThat(selectActivities).contains(activityToAdd1)
    }
}

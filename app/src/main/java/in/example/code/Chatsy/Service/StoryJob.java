package in.example.code.Chatsy.Service;

import android.app.job.JobParameters;
import android.app.job.JobService;

public class StoryJob extends JobService {

    private boolean mRunning = false;
    public StoryJob() {
    }

    @Override
    public boolean onStartJob(JobParameters params) {



        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }


}

package sleeepyy.textrelationmarker;


/**
 * TextRelationMarker
 * Created by sleepy on 2017/12/01.
 */

public interface IResponse {
    void finish(String[] words);
    void failure(String errorMsg);
}
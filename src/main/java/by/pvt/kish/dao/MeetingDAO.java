package by.pvt.kish.dao;

import by.pvt.kish.pojos.Meeting;

/**
 * @author Kish Alexey
 */
public class MeetingDAO extends BaseDAO<Meeting> {

    private static MeetingDAO instance;

    private MeetingDAO() {
        super(Meeting.class);
    }

    public static MeetingDAO getInstance() {
        if (instance == null) {
            instance = new MeetingDAO();
        }
        return instance;
    }
}

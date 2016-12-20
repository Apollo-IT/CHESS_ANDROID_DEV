package com.app.hrms.utils;

public class Urls {
    public static final String PUSH_SERVER  = "www.chess-top.com";
    public static final int    PUSH_PORT    = 3000;

    public static final String BASE_URL = "http://www.chess-top.com:8080";
//	public static final String BASE_URL = "http://192.168.1.177:8080";

    public static final String LOGIN                = "/sys/member/mobile/memberLogin.do";
    public static final String GET_MEMBER           = "/sys/member/mobile/getMember.do";
    public static final String PHOTO                = "/sys/photoimage/downloadPhoto.do?PERNR=";
    public static final String PERSON_BASE          = "/sys/hrmanage/mobile/getPersonBaseInfo.do";
    public static final String SAVE_PERSON_BASE     = "/sys/hrmanage/mobile/savePersonBaseInfo.do";
    public static final String CONTACT_INFO         = "/sys/hrmanage/mobile/getContactInfo.do";
    public static final String SAVE_CONTACT_INFO    = "/sys/hrmanage/mobile/saveContractInfo.do";
    public static final String REMOVE_CONTACT_INFO  = "/sys/hrmanage/mobile/removeContractInfo.do";
    public static final String EVENT_INFO           = "/sys/hrmanage/mobile/getEventInfo.do";
    public static final String SAVE_EVENT_INFO      = "/sys/hrmanage/employee/savePersonEvent.do";
    public static final String REMOVE_EVENT_INFO    = "/sys/hrmanage/employee/removePersonEvent.do";
    public static final String EDUCATION_INFO       = "/sys/hrmanage/mobile/getEducationInfo.do";
    public static final String SAVE_EDUCATION_INFO  = "/sys/hrmanage/mobile/saveEducationInfo.do";
    public static final String REMOVE_EDUCATION_INFO= "/sys/hrmanage/mobile/removeEducationInfo.do";

    public static final String WORKEXP_INFO         = "/sys/hrmanage/mobile/getWorkExpInfo.do";
    public static final String SAVE_WORKEXP_INFO    = "/sys/hrmanage/mobile/saveWorkExperienceInfo.do";
    public static final String REMOVE_WORKEXP_INFO  = "/sys/hrmanage/mobile/removeWorkExperienceInfo.do";
    public static final String TRAIN_INFO           = "/sys/hrmanage/mobile/getTrainInfo.do";
    public static final String SAVE_TRAIN_INFO      = "/sys/hrmanage/mobile/saveTrainInfo.do";
    public static final String REMOVE_TRAIN_INFO    = "/sys/hrmanage/mobile/removeTrainInfo.do";
    public static final String TITLE_INFO           = "/sys/hrmanage/mobile/getTitleInfo.do";
    public static final String SAVE_TITLE_INFO      = "/sys/hrmanage/mobile/saveTitleInfo.do";
    public static final String REMOVE_TITLE_INFO    = "/sys/hrmanage/mobile/removeTitleInfo.do";
    public static final String SKILL_INFO           = "/sys/hrmanage/mobile/getSkillInfo.do";
    public static final String UPLOAD_PHOTO         = "/sys/photoimage/mobile/uploadPhoto.do";

    public static final String SEARCH_COURSE        = "/sys/training/mobile/searchCourse.do";

    // Attendance
    public static final String PUNCH_INFO_FOR_MONTH = "/sys/attendance/mobile/getPunchListForMonth.do";
    public static final String GET_SHIFT_INFO       = "/sys/attendance/punch/mobile/getShiftInfo.do";
    public static final String SAVE_PUNCH_INFO      = "/sys/attendance/punch/mobile/savePunchInfo.do";

    // Salary
    public static final String SALARY_DETAILS       = "/sys/salary/mobile/salaryDetails.do";

    // Performance
    public static final String PERFORMANCE_INFO     = "/sys/hrmanage/mobile/getPerformanceList.do";

    // Training
    public static final String PERSON_EVENT_LIST    = "/sys/hrmanage/mobile/listPersonEvent.do";
    public static final String COURSE_LIST_INFO     = "/sys/training/mobile/courseStatusList.do";
    public static final String COURSE_SAVE_INFO     = "/sys/training/mobile/saveCourse.do";
    public static final String COURSE_UPDATE_INFO   = "/sys/training/mobile/updateCourseStatus.do";

    // Log
    public static final String LOG_LIST_INFO        = "/sys/log/mobile/logList.do";
    public static final String LOG_SAVE_INFO        = "/sys/log/mobile/saveLog.do";
    public static final String LOG_REMOVE           = "/sys/log/mobile/removeLog.do";

    // WorkFlow
    public static final String WORKFLOW_01          = "/sys/workflow/mobile/workFlowList.do?STATUS=01";
    public static final String WORKFLOW_02          = "/sys/workflow/mobile/appliedAppealList.do?STATUS=01";
    public static final String WORKFLOW_03          = "/sys/workflow/mobile/handledAppealList.do";
    public static final String WORKFLOW_04          = "/sys/workflow/mobile/passedAppealList.do";
    public static final String WORKFLOW_05          = "/sys/workflow/mobile/rejectedAppealList.do";
    public static final String WORKFLOW_06          = "/sys/workflow/mobile/totalAppealList.do";

    public static final String WORKFLOW_11          = "/sys/workflow/mobile/approvalList.do?STATUS=02";
    public static final String WORKFLOW_12          = "/sys/workflow/mobile/processedApprovalList.do";
    public static final String WORKFLOW_13          = "/sys/workflow/mobile/finishedApprovalList.do";
    public static final String WORKFLOW_14          = "/sys/workflow/mobile/totalApprovalList.do";

    //http://localhost:8080/sys/workflow/mobile/dailyDetailsApproval.do?ROW_ID=146&memberID=00014058
    public static final String WORKFLOW_DETAILS_01  = "/sys/workflow/mobile/dailyDetailsApproval.do";
    public static final String WORKFLOW_DETAILS_02  = "/sys/workflow/mobile/leaveDetailsApproval.do";
    public static final String WORKFLOW_DETAILS_03  = "/sys/workflow/mobile/travelDetailsApproval.do";
    public static final String WORKFLOW_DETAILS_04  = "/sys/workflow/mobile/overtimeDetailsApproval.do";
    public static final String WORKFLOW_DETAILS_05  = "/sys/workflow/mobile/punchDetailsApproval.do";
    //http://localhost:8080/sys/workflow/mobile/getOldClodaInfo.do?CLODA=2016-07-01&memberID=00014058
    public static final String GET_OLD_CLODA_INFO   = "/sys/workflow/mobile/getOldClodaInfo.do";

    public static final String TASK_LIST            = "/sys/workflow/mobile/taskList.do";
    public static final String NEW_TASK             = "/sys/workflow/mobile/newTask.do";
    public static final String SAVE_TASK            = "/sys/workflow/mobile/saveTask.do";

    public static final String SAVE_APPEAL_DAILY    = "/sys/workflow/mobile/saveDailyAppeal.do";
    public static final String SAVE_APPEAL_LEAVE    = "/sys/workflow/mobile/saveLeaveAppeal.do";
    public static final String SAVE_APPEAL_TRAVEL   = "/sys/workflow/mobile/saveTravelAppeal.do";
    public static final String SAVE_APPEAL_OVERTIME = "/sys/workflow/mobile/saveOvertimeAppeal.do";
    public static final String SAVE_APPEAL_PUNCH    = "/sys/workflow/mobile/savePunchAppeal.do";

    public static final String SAVE_APPROVAL        = "/sys/workflow/mobile/saveApproval.do";

    // subordinate
    public static final String API_ACADEMIC_WEBVIEW  = "/sys/hrmanage/manager/mobile/webview/employinfo.do?memberID=";
    public static final String API_ATTENDANCE_WEBVIEW= "/sys/hrmanage/manager/mobile/webview/attendance.do?memberID=";
    public static final String API_SALARY_WEBVIEW    = "/sys/hrmanage/manager/mobile/webview/salary.do?memberID=";
    public static final String API_ASSESS_WEBVIEW    = "/sys/hrmanage/manager/mobile/webview/assess.do?memberID=";
    public static final String API_TASK_WEBVIEW      = "/sys/hrmanage/manager/mobile/subordinates/taskWebView.do?memberID=";
    public static final String API_TRAINING_WEBVIEW  = "/sys/hrmanage/manager/mobile/webview/training.do";

    public static final String API_SUBORDINATES_LIST = "/sys/hrmanage/manager/mobile/subordinates/list.do";
    public static final String API_ACADEMIC_CHART    = "/sys/hrmanage/manager/mobile/subordinates/academicChart.do";
    public static final String API_ATTENDANCE_CHART  = "/sys/hrmanage/manager/mobile/subordinates/attendanceChart.do";
    public static final String API_SALARY_CHART      = "/sys/hrmanage/manager/mobile/subordinates/salaryChart.do";
    public static final String API_TASK_CHART        = "/sys/hrmanage/manager/mobile/subordinates/taskChart.do";
    public static final String API_ASSESS_CHART      = "/sys/hrmanage/manager/mobile/subordinates/assessChart.do";
    public static final String SURBO_EVENT_LIST      = "/sys/hrmanage/manager/mobile/subordinates/listTrainingEvent.do";

    public static final String API_TRAINING_CHART    = "/sys/hrmanage/manager/mobile/subordinates/trainingChart.do";

    //http://www.chess-top.com:8080/sys/hrmanage/mobile/listPersonEvent.do?objid=00014115&type=PA1008&page=1&rows=10
    public static final String API_CONTACT_INFO      = "/sys/hrmanage/mobile/listPersonEvent.do?type=PA1008&page=1&rows=10";
    public static final String API_SAVE_PHONE_NUMBER = "/sys/hrmanage/employee/mobile/saveCommunicationInfo.do";

    public static final String API_ORG_UNIT_TREE     = "/sys/organize/mobile/listOrgUnitTreeJsonData.do";

    public static final String API_SEND_EMAIL        = "/sys/member/mobile/sendSystemEmail.do";

}

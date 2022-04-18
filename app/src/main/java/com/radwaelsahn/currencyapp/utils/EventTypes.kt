package com.radwaelsahn.currencyapp.utils

sealed class NavigationEvent {
    object DashboardActivity : NavigationEvent()
    object HomeActivity : NavigationEvent()
    object LoginPromptActivity : NavigationEvent()
    object AccountDetails : NavigationEvent()
    object AccountDetailsWithData : NavigationEvent()
    object ChooseLanguage : NavigationEvent()
    object RequirePhone : NavigationEvent()
}

sealed class GenericEvent {
    class Error(val errorMessage: String, val source: Int? = null) : GenericEvent()
    class BooleanResponse(val isSuccess: Boolean, val source: Int? = null) : GenericEvent()
    class ProgressVisibility(val isVisible : Boolean) : GenericEvent()
}

sealed class SubjectDetailEvent() {
    object PaymentActivity : SubjectDetailEvent()
    class EmailDialog(val triggerPage: Int) : SubjectDetailEvent()
    object SubscriptionRequired : SubjectDetailEvent()
    object SubscriptionControl : SubjectDetailEvent()
    object SubscriptionControlForReview : SubjectDetailEvent()
    object ShareSubject : SubjectDetailEvent()
    object LoggedInSuccessfully : SubjectDetailEvent()
}

sealed class PackageDetailEvent() {
    object PaymentActivity : PackageDetailEvent()
    class EmailDialog(val triggerPage: Int) : PackageDetailEvent()
    object SubscriptionRequired : PackageDetailEvent()
    object SubscriptionControl : PackageDetailEvent()
}

sealed class CourseDetailEvent() {
    object PaymentActivity : CourseDetailEvent()
    class EmailDialog(val triggerPage: Int) : CourseDetailEvent()
    object LoggedInSuccessfully : CourseDetailEvent()
    object SubscriptionRequired : CourseDetailEvent()
    object SubscriptionControl : CourseDetailEvent()
    object SubscriptionControlForReview : CourseDetailEvent()
    object ShareCourse : CourseDetailEvent()
}

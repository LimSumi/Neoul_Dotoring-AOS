package com.example.dotoring_neoul.ui.register.fourth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.dotoring_neoul.R
import com.example.dotoring_neoul.navigation.AuthScreen
import com.example.dotoring_neoul.ui.theme.DotoringTheme
import com.example.dotoring_neoul.ui.util.TopRegisterScreen
import com.example.dotoring_neoul.ui.util.register.MenteeInformation
import com.example.dotoring_neoul.ui.util.register.MentorInformation
import com.example.dotoring_neoul.ui.util.register.RegisterScreenNextButton

/**
 * 네번째 회원 가입 화면 composable
 */
@Composable
fun FourthRegisterScreen(
    registerFourthViewModel: RegisterFourthViewModel = viewModel(),
    navController: NavHostController,
    mentorInformation: MentorInformation?,
    menteeInformation: MenteeInformation?,
    isMentor: Boolean
) {
    val registerFourthUiState by registerFourthViewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    val question = if(isMentor) {
        R.string.register4_q4_mentor
    } else {
        R.string.register4_q4_mentee
    }
    Row {
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier
                .requiredWidth(width = 320.dp)
        ) {
            TopRegisterScreen(
                screenNumber = 4,
                question = question,
                guide = stringResource(id = R.string.register4_guide),
                isMentor = isMentor
            )
            Spacer(modifier = Modifier.weight(1f))


            Column {
                Text(text = stringResource(id = R.string.register_A))
                Spacer(modifier = Modifier.size(10.dp))
                RoundedCornerTextField(
                    value = registerFourthUiState.memberIntroduction,
                    onValueChange = {
                        registerFourthViewModel.updateIntroductionInput(it)

                        if(it.length in 10..80) {
                            registerFourthViewModel.updateNextButtonState(true)
                        } else {
                            registerFourthViewModel.updateNextButtonState(false)
                        }
                    },
                    onDone = {
                        focusManager.clearFocus()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp))
                        .background(colorResource(R.color.grey_200))
                        .padding(16.dp)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = if(!registerFourthUiState.nextButtonState) {
                        stringResource(id = R.string.register4_error_condition_not_satisfied)
                    } else {
                        ""
                    },
                    modifier = Modifier
                        .padding(start = 2.dp, top = 3.dp),
                    color = Color(0xffff7B7B),
                    fontSize = 10.sp
                )
            }
            Spacer(modifier = Modifier.weight(1f))


            RegisterScreenNextButton(
                onClick = {
                    if(isMentor) {
                        if(mentorInformation != null) {
                            val mentorInfo = MentorInformation(
                                company = mentorInformation.company,
                                careerLevel = mentorInformation.careerLevel,
                                field = mentorInformation.field,
                                major = mentorInformation.major,
                                employmentCertification = mentorInformation.employmentCertification,
                                graduateCertification = mentorInformation.graduateCertification,
                                nickname = mentorInformation.nickname,
                                introduction = registerFourthUiState.memberIntroduction
                            )
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key = "mentorInfo",
                                value = mentorInfo
                            )
                        }
                    } else {
                        if(menteeInformation != null) {
                            val menteeInfo = MenteeInformation(
                                school = menteeInformation.school,
                                grade = menteeInformation.grade,
                                field = menteeInformation.field,
                                major = menteeInformation.major,
                                enrollmentCertification = menteeInformation.enrollmentCertification,
                                nickname = menteeInformation.nickname,
                                introduction = registerFourthUiState.memberIntroduction
                            )
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key = "menteeInfo",
                                value = menteeInfo
                            )
                        }
                    }
                    navController.navigate(AuthScreen.Register5.passScreenState(isMentor))
                },
                enabled = registerFourthUiState.nextButtonState,
                isMentor = isMentor
            )
            Spacer(modifier = Modifier.weight(5f))
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

/**
 * 회원 가입 네번째 화면 텍스트필드 composable
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RoundedCornerTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hintText: String = "",
    textStyle: TextStyle = MaterialTheme.typography.body1,
    maxLines: Int = 5,
    onDone: (KeyboardActionScope.() -> Unit)?
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        maxLines = maxLines,
        decorationBox = {innerTextField ->
            Box(modifier = modifier) {
                if(value.isEmpty()) {
                    Text(
                        text = hintText,
                        color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
                    )
                }
                innerTextField()
            }
        }
    )
}

/**
 * 회원 가입 네번째 화면 미리보기
 */
@Preview(showSystemUi = true)
@Composable
private fun RegisterScreenPreview() {
    DotoringTheme {
        FourthRegisterScreen(
            navController = rememberNavController(),
            mentorInformation = MentorInformation(),
            menteeInformation = MenteeInformation(),
            isMentor = true
        )
    }
}
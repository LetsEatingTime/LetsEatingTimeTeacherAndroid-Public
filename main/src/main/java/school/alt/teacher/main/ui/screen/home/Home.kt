package school.alt.teacher.main.ui.screen.home

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import school.alt.teacher.main.navigation.ScreenNavigate
import school.alt.teacher.main.ui.compose.button.TextButton1
import school.alt.teacher.main.ui.compose.dialog.LetAlertDialog
import school.alt.teacher.main.ui.compose.menu.CustomMenu1ViewPager
import school.alt.teacher.main.ui.compose.menu.CustomMenu2ViewPager
import school.alt.teacher.main.ui.compose.menu.MenuBar1
import school.alt.teacher.main.ui.compose.prize.PrizeItem
import school.alt.teacher.main.ui.compose.shimmer.ShimmerProduction
import school.alt.teacher.main.ui.foundation.IcAdd
import school.alt.teacher.main.ui.foundation.IcAddPeople
import school.alt.teacher.main.ui.foundation.IcEnter
import school.alt.teacher.main.ui.foundation.IcScan
import school.alt.teacher.main.ui.theme.backgroundStrong
import school.alt.teacher.main.ui.theme.black
import school.alt.teacher.main.ui.theme.line1
import school.alt.teacher.main.ui.theme.main2
import school.alt.teacher.main.ui.theme.pretendard
import school.alt.teacher.main.ui.theme.white
import school.alt.teacher.main.viewmodel.home.HomeViewModel
import school.alt.teacher.main.viewmodel.home.LeftoversDataState
import school.alt.teacher.main.viewmodel.store.ProductImageUiState
import school.alt.teacher.main.viewmodel.store.ProductUiState
import school.alt.teacher.main.viewmodel.store.StoreViewModel
import school.alt.teacher.main.viewmodel.student.StudentViewModel
import school.alt.teacher.network.dto.ProductDto

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel,
    studentViewModel: StudentViewModel,
    storeViewModel: StoreViewModel,
    onMoveScreen: (String) -> Unit,
) {

    val menuPagerState = rememberPagerState {
        3 // 총 페이지 수 설정
    }
    val pagerState = rememberPagerState {
        4 // 총 페이지 수 설정
    }
    val mainScrollState = rememberScrollState()
    val context = LocalContext.current

    val itemsIndexedList = remember {
        mutableStateOf<List<ProductDto>?>(null)
    }

    val mealState by homeViewModel.mealUiState.collectAsState()
    val productionList by storeViewModel.productList.collectAsState()
    val productionImageList by storeViewModel.productImageList.collectAsState()
    val homeDialogUiState by homeViewModel.userDialogUiState.collectAsState()
    val leftoversDataUiState by homeViewModel.leftoversDataUiState.collectAsState()
    DisposableEffect(Unit) {
        val scope = CoroutineScope(Dispatchers.Default)
        val job = scope.launch {
            homeViewModel.initData()
            storeViewModel.getProductAllData {
                itemsIndexedList.value = storeViewModel.products.value!!// ViewModel에서 값 불러와서 저장하기
            }
            homeViewModel.getLeftoversData()
        }

        onDispose {
            job.cancel()
        }
    }

    LaunchedEffect(true) {
        storeViewModel.getLiveAllProduct()
    }

    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition(label = "")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )


    LaunchedEffect(storeViewModel.products.value) {
        itemsIndexedList.value = storeViewModel.products.value ?: listOf()// ViewModel에서 값 불러와서 저장하기
    }

    Column(
        modifier = Modifier
            .background(color = backgroundStrong)
            .fillMaxSize()
            .verticalScroll(state = mainScrollState)
    ) {

        Column(Modifier.padding(top = 25.dp, bottom = 12.dp)) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 2.dp, end = 1.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "오늘의 급식",
                    color = black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = pretendard
                )
                Row(
                    modifier = Modifier
                        .clickable(
                        ) {
                            onMoveScreen(ScreenNavigate.SHOW_MENU.name)
                        },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "더보기",
                        color = black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        fontFamily = pretendard
                    )
                    Spacer(modifier = Modifier.padding(start = 8.dp))
                    IcEnter(contentDescription = "", size = 20.dp)
                }
            }
            Spacer(modifier = Modifier.padding(top = 12.dp))
            CustomMenu2ViewPager(
                pagerState = menuPagerState,
                menuState = mealState,
            )
        }
        Column(Modifier.padding(top = 12.dp, bottom = 12.dp)) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 2.dp, end = 1.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "지속적으로 관리 해요",
                    color = black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = pretendard
                )
            }
            Spacer(modifier = Modifier.padding(top = 12.dp))
            Row(
                modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton1(text = "메뉴추가",
                    backgroundColor = main2,
                    paddingValues = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                    icon = { IcScan(contentDescription = "", size = 47.dp, tint = white) }) {
                    onMoveScreen(ScreenNavigate.MENU_ADD_TITLE.name)
                }
                TextButton1(text = "학생 회원가입 승인",
                    backgroundColor = white,
                    textColor = line1,
                    paddingValues = PaddingValues(horizontal = 1.dp, vertical = 8.dp),
                    textSize = 14.sp,
                    icon = { IcAddPeople(contentDescription = "", size = 50.dp, tint = line1) }) {
                    studentViewModel.getApprovalList()
                    onMoveScreen(ScreenNavigate.STUDENT_APPROVAL.name)
                }
            }
        }
        Column(Modifier.padding(top = 12.dp, bottom = 12.dp)) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 2.dp, end = 1.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "학생 호감 메뉴",
                    color = black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = pretendard
                )
            }
            Spacer(modifier = Modifier.padding(top = 12.dp))
            MenuBar1(
                breakfastMenu = "망고스틱",
                lunchMenu = "백김치",
                dinnerMenu = "만석이닭강정"
            )
        }

        Column(Modifier.padding(top = 12.dp, bottom = 12.dp)) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 2.dp, end = 1.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "학생 비 호감 메뉴",
                    color = black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = pretendard
                )
            }
            Spacer(modifier = Modifier.padding(top = 12.dp))
            when (leftoversDataUiState) {
                is LeftoversDataState.Success -> {
                    val menuData = (leftoversDataUiState as LeftoversDataState.Success).foods
                    if (menuData.isNotEmpty()) {
                        MenuBar1(
                            breakfastMenu = menuData[0] ?: "없음",
                            lunchMenu = menuData[1] ?: "없음",
                            dinnerMenu = menuData[2] ?: "없음"
                        )
                    }
                }

                is LeftoversDataState.Error -> {
                    MenuBar1(
                        breakfastMenu = "",
                        lunchMenu = "",
                        dinnerMenu = ""
                    )
                }
            }
        }

        Column(Modifier.padding(top = 12.dp, bottom = 12.dp)) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 2.dp, end = 1.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "학생 평균 잔반 제로수",
                    color = black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = pretendard
                )
            }
            Spacer(modifier = Modifier.padding(top = 12.dp))
            CustomMenu1ViewPager(
                pagerState, true, listOf(
                    listOf("60%", "80%", "70%"),
                    listOf("97%", "93%", "84%"),
                    listOf("59%", "75%", "100%"),
                    listOf("13%", "90%", "41%")
                )
            )
        }

        Column(Modifier.padding(top = 12.dp, bottom = 12.dp)) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 2.dp, end = 1.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "상품 추가 및 수정",
                    color = black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = pretendard
                )
                IconButton(
                    onClick = {
                        storeViewModel.setIsEdit(false)
                        onMoveScreen(ScreenNavigate.STORE.name)
                    }
                ) {
                    IcAdd(
                        modifier = Modifier, contentDescription = "", tint = main2, size = 21.dp
                    )
                }
            }

            Spacer(modifier = Modifier.padding(top = 12.dp))
            when (productionList) {
                is ProductUiState.Success -> {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 29.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        itemsIndexed((productionList as ProductUiState.Success).products) { index, item ->
                            PrizeItem(
                                modifier = Modifier.clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    storeViewModel.setData(item)
                                    onMoveScreen(ScreenNavigate.STORE_MAIN.name)
                                },
                                prizeTitle = item.productName,
                                prizePrice = item.price.toString(),
                                prizeUrl =
                                when (productionImageList) {
                                    is ProductImageUiState.Success -> {
                                        (productionImageList as ProductImageUiState.Success).products[item.image]?.fileName
                                            ?: ""
                                    }

                                    is ProductImageUiState.Error -> {
                                        ""
                                    }
                                }
                            ) {
                                homeViewModel.showProductDialog(
                                    onModify = {
                                        storeViewModel.setIsEdit(true)
                                        storeViewModel.setData(item)
                                        onMoveScreen(ScreenNavigate.STORE.name)
                                    })
                            }
                        }
                    }
                }

                is ProductUiState.Error -> {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 29.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        itemsIndexed(listOf("", "", "")) { _, _ ->
                            ShimmerProduction(brush = brush)
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.padding(top = 70.dp))
    }
    if (homeDialogUiState.title != "") {
        LetAlertDialog(
            title = homeDialogUiState.title,
            subTitle = homeDialogUiState.description,
            description = homeDialogUiState.description,
            setNegativeText = {
                homeDialogUiState.negativeComment()
            },
            setPositiveText = {
                homeDialogUiState.positiveComment()
            },
            onClickNegative = homeDialogUiState.onClickNegative,
            onClickPositive = homeDialogUiState.onClickPositive
        )
    }
}

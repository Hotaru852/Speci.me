package com.example.specime.screens.home

import androidx.compose.ui.graphics.Color
import com.example.specime.R
import com.google.firebase.Timestamp

data class HomeScreenState(
    val profilePictureUrl: String? = "",
    val isLoading: Boolean = true,
    val testResults: List<TestResult>? = emptyList(),
    val groupInformations : List<GroupInformation>? = emptyList(),
    val isChoosingTestOption: Boolean = false,
    val isShowingTraitDetail: Boolean = false
)

data class TestResult(
    val result: Map<String, Int>,
    val trait: String?,
    val timestamp: Timestamp?,
    val detailId: String?,
    val groupName: String?
)

data class GroupInformation(
    val groupName: String?,
    val timestamp: Timestamp?,
    val testsDone: Int?,
    val groupSize: Int?,
    val groupId: String?
)

sealed class Trait(
    val title: String,
    val imageRes: Int,
    val description: String,
    val characteristics: List<String>,
    val strengths: List<String>,
    val weaknesses: List<String>,
    val color: Color
) {
    data object Eagle : Trait(
        title = "ĐẠI BÀNG",
        imageRes = R.drawable.eagle,
        description = "Đại Bàng đại diện cho nhóm tính cách Dominance (D) - những người quyết đoán, tập trung vào kết quả và không ngại đối mặt với thử thách. Họ là những nhà lãnh đạo bẩm sinh với tầm nhìn xa, luôn hướng đến mục tiêu và chinh phục đỉnh cao.",
        characteristics = listOf(
            "Quyết đoán và tự tin cao",
            "Định hướng kết quả và hiệu quả",
            "Dám chấp nhận rủi ro, thích thử thách",
            "Tư duy chiến lược và tầm nhìn tổng thể"
        ),
        strengths = listOf(
            "Lãnh đạo mạnh mẽ và truyền cảm hứng",
            "Giải quyết vấn đề nhanh chóng và hiệu quả",
            "Dám đưa ra quyết định khó khăn",
            "Khả năng tổ chức và điều phối đội nhóm xuất sắc"
        ),
        weaknesses = listOf(
            "Có thể trở nên độc đoán và áp đặt",
            "Thiếu kiên nhẫn với người chậm hơn",
            "Đôi khi bỏ qua chi tiết quan trọng",
            "Khó lắng nghe ý kiến đóng góp từ người khác"
        ),
        color = Color(0xFFFF4D4D)
    )

    data object Peacock : Trait(
        title = "CHIM CÔNG",
        imageRes = R.drawable.peacock,
        description = "Chim Công đại diện cho nhóm tính cách Influence (I) - những người hướng ngoại, nhiệt tình và giàu cảm xúc. Họ là linh hồn của bữa tiệc, có khả năng giao tiếp xuất sắc và luôn tạo không khí vui vẻ, phấn khởi xung quanh mình.",
        characteristics = listOf(
            "Hướng ngoại và hoạt bát",
            "Nhiệt tình và lạc quan",
            "Giỏi thuyết phục và truyền cảm hứng",
            "Thích giao lưu và kết nối mọi người"
        ),
        strengths = listOf(
            "Kỹ năng giao tiếp và thuyết trình tuyệt vời",
            "Khả năng kết nối và xây dựng mạng lưới quan hệ",
            "Sáng tạo và đổi mới trong giải quyết vấn đề",
            "Tạo động lực và không khí tích cực cho nhóm"
        ),
        weaknesses = listOf(
            "Đôi khi nói nhiều hơn lắng nghe",
            "Có thể thiếu kiên trì với các nhiệm vụ chi tiết",
            "Dễ bị phân tâm và mất tập trung",
            "Thường đưa ra quyết định dựa trên cảm xúc"
        ),
        color = Color(0xFFFFD700)
    )

    data object Dove : Trait(
        title = "BỒ CÂU",
        imageRes = R.drawable.dove,
        description = "Bồ Câu đại diện cho nhóm tính cách Steadiness (S) - những người điềm tĩnh, kiên nhẫn và đáng tin cậy. Họ luôn hướng đến sự hài hòa, ổn định và là chỗ dựa vững chắc cho mọi người xung quanh.",
        characteristics = listOf(
            "Kiên nhẫn và điềm tĩnh",
            "Đáng tin cậy và trung thành",
            "Biết lắng nghe và thấu hiểu",
            "Thích môi trường ổn định và hài hòa"
        ),
        strengths = listOf(
            "Khả năng làm việc nhóm xuất sắc",
            "Tạo dựng môi trường làm việc hòa thuận",
            "Giải quyết xung đột một cách khéo léo",
            "Kiên trì và theo đuổi mục tiêu đến cùng"
        ),
        weaknesses = listOf(
            "Khó thích nghi với thay đổi đột ngột",
            "Đôi khi quá nhường nhịn và ngại đối đầu",
            "Có thể chậm trong việc ra quyết định",
            "Ngại bày tỏ ý kiến cá nhân trong nhóm đông"
        ),
        color = Color(0xFF4CAF50)
    )

    data object Owl : Trait(
        title = "CHIM CÚ",
        imageRes = R.drawable.owl,
        description = "Chim Cú đại diện cho nhóm tính cách Conscientiousness (C) - những người phân tích, chi tiết và cầu toàn. Họ luôn theo đuổi sự hoàn hảo, có tư duy logic và đánh giá cao chất lượng trong mọi việc.",
        characteristics = listOf(
            "Phân tích và tư duy logic",
            "Tỉ mỉ và chú trọng chi tiết",
            "Cầu toàn và đề cao chất lượng",
            "Thận trọng và có tính kỷ luật cao"
        ),
        strengths = listOf(
            "Giải quyết vấn đề phức tạp một cách hiệu quả",
            "Lập kế hoạch chi tiết và tổ chức tốt",
            "Đánh giá tình huống khách quan, công bằng",
            "Đảm bảo chất lượng và độ chính xác cao"
        ),
        weaknesses = listOf(
            "Có thể quá chú trọng vào chi tiết mà bỏ qua bức tranh tổng thể",
            "Đôi khi quá phê phán và khó tính",
            "Ngại rủi ro và thay đổi",
            "Có thể chậm trong việc ra quyết định vì muốn có đủ thông tin"
        ),
        color = Color(0xFF2196F3)
    )
}
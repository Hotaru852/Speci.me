package com.example.specime.screens.result

data class ResultState (
    val result: Map<String, Int>? = null,
    val isLoading: Boolean = true,
    val testTaken: Boolean = false,
    val personality: String? = null,
    val discDescriptions: Map<String, String> = mapOf(
        "D" to "người quyết đoán, thích dẫn đầu và luôn đạt được mục tiêu một cách nhanh chóng",
        "I" to "người truyền cảm hứng, lạc quan và luôn thu hút mọi người xung quanh",
        "S" to "người đáng tin cậy, kiên nhẫn và luôn hỗ trợ người khác",
        "C" to "người có tính kỷ luật, luôn chú trọng đến chi tiết và tuân thủ các quy tắc",
        "DI" to "người vừa quyết đoán vừa năng động, luôn tìm kiếm cơ hội mới và không ngại thử thách",
        "DS" to "người mạnh mẽ và kiên định, có khả năng thích ứng nhanh với môi trường mới",
        "DC" to "người quyết đoán nhưng thận trọng, luôn đảm bảo mọi thứ được thực hiện đúng cách",
        "IS" to "người thân thiện, kiên nhẫn và luôn mang lại cảm giác thoải mái cho người khác",
        "IC" to "người sáng tạo, có tổ chức và luôn truyền cảm hứng cho mọi người",
        "SC" to "người đáng tin cậy, có tính kỷ luật và luôn duy trì chất lượng trong công việc",
        "DIS" to "người lãnh đạo mạnh mẽ và đồng cảm, biết cách kết nối và dẫn dắt đồng nghiệp",
        "DIC" to "người sáng tạo, quyết đoán và luôn tập trung vào kết quả",
        "ISC" to "người dễ gần, có tính kỷ luật và luôn duy trì chất lượng trong công việc",
        "DSC" to "người có kế hoạch rõ ràng, kiên định và quyết tâm đạt được mục tiêu",
        "DISC" to "người toàn diện, vừa sáng tạo, quyết đoán, ổn định và tỉ mỉ trong công việc"
    )
)
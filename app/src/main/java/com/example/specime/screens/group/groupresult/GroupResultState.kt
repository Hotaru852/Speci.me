package com.example.specime.screens.group.groupresult

import com.example.specime.screens.home.TestResult

data class GroupResultState(
    val groupName: String? = "",
    val currentUserLatestTestResult: TestResult? = null,
    val memberTestResults: List<MemberTestResult>? = emptyList(),
    val isLoading: Boolean = false,
    val isShowingCompatibility: Boolean = false,
    val selectedCompatibility: Compatibility? = null
)

data class MemberTestResult(
    val trait: String?,
    val username: String,
    val email: String
)

data class Compatibility(
    val trait1: String,
    val trait2: String,
    val compatibilityLevel: String,
    val description: String,
    val challenges: String,
    val successStrategies: String,
    val workRelationship: String
)

object TraitCompatibilityMatrix {
    private val compatibilityMap = mapOf(
        Pair("Đại Bàng", "Đại Bàng") to Compatibility(
            trait1 = "Đại Bàng",
            trait2 = "Đại Bàng",
            compatibilityLevel = "Thấp đến Trung bình",
            description = "Hai Đại Bàng cùng làm việc tạo ra môi trường đầy năng lượng và định hướng mục tiêu rõ rệt. Họ đều quyết đoán, tự tin và hướng đến kết quả nhanh chóng. Không gian làm việc của họ sôi động, hiệu quả nhưng cũng tiềm ẩn cạnh tranh cao.",
            challenges = "Hai Đại Bàng thường xuyên tranh giành quyền lãnh đạo và không gian ảnh hưởng. Họ khó nhượng bộ và thỏa hiệp, dẫn đến xung đột quyền lực. Cả hai đều muốn làm theo cách của mình, thường thiếu kiên nhẫn với ý kiến khác và có khuynh hướng áp đặt quan điểm.",
            successStrategies = "Cần xác định rõ vai trò, trách nhiệm và ranh giới quyền hạn ngay từ đầu. Phát triển kỹ năng lắng nghe chủ động và tôn trọng chuyên môn của đối phương. Thiết lập quy trình giải quyết xung đột công bằng. Nhận diện và tập trung vào mục tiêu chung thay vì cạnh tranh cá nhân.",
            workRelationship = "Khi học được cách hợp tác, hai Đại Bàng có thể tạo nên đội ngũ mạnh mẽ với tốc độ thực hiện cao. Họ thúc đẩy nhau vươn tới thành tích xuất sắc và có thể đạt được những mục tiêu đầy thách thức. Môi trường làm việc này đòi hỏi sự tôn trọng lẫn nhau và cơ chế giải quyết mâu thuẫn hiệu quả."
        ),
        Pair("Đại Bàng", "Chim Công") to Compatibility(
            trait1 = "Đại Bàng",
            trait2 = "Chim Công",
            compatibilityLevel = "Trung bình",
            description = "Đại Bàng và Chim Công tạo nên sự kết hợp giữa sức mạnh và sự quyến rũ. Đại Bàng mang đến tầm nhìn chiến lược và khả năng ra quyết định, trong khi Chim Công thể hiện xuất sắc trong giao tiếp và xây dựng mối quan hệ. Môi trường làm việc của họ cân bằng giữa định hướng mục tiêu và không khí tích cực.",
            challenges = "Đại Bàng có thể thiếu kiên nhẫn với phong cách giao tiếp cảm xúc và chi tiết của Chim Công. Ngược lại, Chim Công dễ cảm thấy bị áp đảo bởi sự trực diện và mạnh mẽ của Đại Bàng. Cách tiếp cận ra quyết định khác biệt - một bên theo số liệu và kết quả, bên kia theo cảm xúc và mối quan hệ - thường gây hiểu lầm.",
            successStrategies = "Đại Bàng cần rèn luyện sự kiên nhẫn và đánh giá cao tài năng giao tiếp của Chim Công. Chim Công nên học cách đánh giá sự thẳng thắn của Đại Bàng và rèn luyện tính tập trung vào mục tiêu. Xây dựng lòng tin qua giao tiếp minh bạch và phân định vai trò rõ ràng: Đại Bàng dẫn dắt chiến lược, Chim Công xây dựng mối quan hệ.",
            workRelationship = "Sự hợp tác giữa Đại Bàng và Chim Công có thể cực kỳ hiệu quả khi mỗi người phát huy thế mạnh của mình. Đại Bàng đảm bảo dự án tiến về phía trước, trong khi Chim Công tạo động lực và kết nối mọi người. Qua thời gian, Đại Bàng có thể học được cách giao tiếp mềm mại hơn, còn Chim Công trở nên tập trung và định hướng kết quả hơn."
        ),
        Pair("Đại Bàng", "Bồ Câu") to Compatibility(
            trait1 = "Đại Bàng",
            trait2 = "Bồ Câu",
            compatibilityLevel = "Thấp",
            description = "Đại Bàng và Bồ Câu thể hiện sự tương phản mạnh mẽ trong phong cách làm việc. Đại Bàng quyết đoán, nhanh chóng và định hướng kết quả, trong khi Bồ Câu nhẹ nhàng, thận trọng và hướng đến sự hài hòa. Môi trường làm việc của họ có thể kết hợp giữa hiệu quả và sự ổn định, nhưng đòi hỏi nhiều nỗ lực để dung hòa.",
            challenges = "Đại Bàng thường mất kiên nhẫn với tốc độ cẩn trọng và xu hướng tránh xung đột của Bồ Câu. Bồ Câu dễ cảm thấy bị áp lực, căng thẳng trước phong cách mạnh mẽ của Đại Bàng và thường không bày tỏ quan điểm thật sự của mình. Khoảng cách về nhịp độ làm việc và phong cách giao tiếp (trực tiếp đối với gián tiếp) thường gây hiểu lầm và bất đồng.",
            successStrategies = "Đại Bàng cần học cách làm chậm lại, lắng nghe sâu và tạo không gian an toàn cho Bồ Câu bày tỏ quan điểm. Bồ Câu cần rèn luyện sự tự tin để chia sẻ ý kiến, thiết lập giới hạn rõ ràng và bày tỏ nhu cầu của mình. Cả hai cần xây dựng quy tắc giao tiếp tôn trọng và công nhận giá trị đóng góp của nhau.",
            workRelationship = "Mối quan hệ này đòi hỏi sự thích nghi từ cả hai phía, nhưng có thể bổ sung cho nhau hiệu quả. Đại Bàng mang lại tầm nhìn và động lực thúc đẩy, Bồ Câu đóng góp sự ổn định và chi tiết tỉ mỉ. Bồ Câu có thể giúp Đại Bàng phát triển sự kiên nhẫn và kỹ năng lắng nghe, trong khi Đại Bàng khuyến khích Bồ Câu tự tin hơn và dám đối mặt với thách thức."
        ),
        Pair("Đại Bàng", "Chim Cú") to Compatibility(
            trait1 = "Đại Bàng",
            trait2 = "Chim Cú",
            compatibilityLevel = "Cao",
            description = "Đại Bàng và Chim Cú tạo nên sự kết hợp hiệu quả giữa tầm nhìn chiến lược và phân tích chuyên sâu. Đại Bàng mang đến quyết đoán và định hướng tổng thể, trong khi Chim Cú đóng góp sự cẩn trọng và phân tích chi tiết. Cả hai đều hướng đến kết quả nhưng với cách tiếp cận bổ sung cho nhau, tạo nên môi trường làm việc cân bằng giữa tốc độ và độ chính xác.",
            challenges = "Đại Bàng thường muốn tiến nhanh hơn và có thể thiếu kiên nhẫn với quá trình phân tích kỹ lưỡng của Chim Cú. Ngược lại, Chim Cú có thể cho rằng Đại Bàng bỏ qua những chi tiết quan trọng và vội vàng trong quyết định. Khác biệt về nhịp độ làm việc có thể tạo ra căng thẳng: Đại Bàng mong muốn kết quả ngay lập tức, trong khi Chim Cú cần thời gian để đảm bảo mọi yếu tố đều hoàn hảo.",
            successStrategies = "Đại Bàng cần trân trọng giá trị từ phân tích chi tiết của Chim Cú và hiểu rằng điều này sẽ dẫn đến quyết định tốt hơn. Chim Cú nên học cách trình bày thông tin ngắn gọn, tập trung vào những điểm quan trọng nhất để đáp ứng nhu cầu về tốc độ của Đại Bàng. Thiết lập lịch trình rõ ràng với các mốc thời gian hợp lý cho cả phân tích và hành động.",
            workRelationship = "Khi hợp tác hiệu quả, Đại Bàng và Chim Cú tạo nên đội ngũ mạnh mẽ với khả năng giải quyết vấn đề xuất sắc. Đại Bàng đảm bảo dự án luôn tiến về phía trước với tốc độ phù hợp, trong khi Chim Cú đảm bảo mọi chi tiết đều được xem xét kỹ lưỡng. Sự kết hợp này thường dẫn đến những quyết định chất lượng cao và kết quả vượt trội."
        ),
        Pair("Chim Công", "Chim Công") to Compatibility(
            trait1 = "Chim Công",
            trait2 = "Chim Công",
            compatibilityLevel = "Trung bình đến Cao",
            description = "Hai Chim Công làm việc cùng nhau tạo nên môi trường cực kỳ sôi động, vui vẻ và giàu năng lượng tích cực. Không khí làm việc đầy sự nhiệt tình, cởi mở và luôn tràn ngập những ý tưởng sáng tạo. Họ tạo ra không gian nơi mọi người cảm thấy được hoan nghênh, giao tiếp diễn ra liên tục và mạng lưới mối quan hệ được xây dựng mạnh mẽ.",
            challenges = "Môi trường làm việc có thể thiếu tổ chức và kỷ luật. Hai Chim Công dễ bị cuốn vào các cuộc trò chuyện thú vị mà quên mất nhiệm vụ cần hoàn thành. Họ có thể liên tục sinh ra ý tưởng mới nhưng thiếu kiên trì để hoàn thiện chúng. Quản lý thời gian kém, thường bỏ lỡ thời hạn và thiếu kế hoạch chi tiết. Có khuynh hướng tránh những cuộc đối thoại khó khăn và xung đột cần thiết.",
            successStrategies = "Cần xây dựng hệ thống quản lý thời gian và trách nhiệm rõ ràng. Phân công người có tính cách khác để theo dõi tiến độ và đảm bảo các nhiệm vụ được hoàn thành. Đặt ra các mục tiêu và thời hạn cụ thể, sử dụng công cụ nhắc nhở. Tạo không gian cho sự sáng tạo nhưng kết hợp với cấu trúc để đảm bảo kết quả. Học cách đương đầu với những cuộc trò chuyện khó khăn khi cần thiết.",
            workRelationship = "Hai Chim Công tạo nên không khí làm việc vui vẻ, nhiệt tình và đầy cảm hứng. Họ xuất sắc trong việc xây dựng mối quan hệ với khách hàng và đồng nghiệp, tạo nên môi trường đoàn kết. Họ hỗ trợ và động viên nhau về mặt cảm xúc, cùng chia sẻ niềm vui và nhiệt huyết. Tuy nhiên, cần có sự hỗ trợ từ những người có tính cách khác để đảm bảo các ý tưởng sáng tạo được hiện thực hóa."
        ),
        Pair("Chim Công", "Bồ Câu") to Compatibility(
            trait1 = "Chim Công",
            trait2 = "Bồ Câu",
            compatibilityLevel = "Cao",
            description = "Chim Công và Bồ Câu tạo nên sự kết hợp hài hòa giữa nhiệt huyết và sự ổn định. Chim Công mang đến năng lượng tích cực, sự sáng tạo và niềm vui, trong khi Bồ Câu đóng góp sự bình tĩnh, đáng tin cậy và nhất quán. Môi trường làm việc của họ vừa năng động vừa an toàn, tạo không gian cho mọi người phát triển và cảm thấy được hỗ trợ.",
            challenges = "Chim Công có thể quá nhanh, thay đổi liên tục và nhiệt tình đến mức Bồ Câu khó theo kịp. Bồ Câu đôi khi cảm thấy choáng ngợp trước dòng ý tưởng không ngừng của Chim Công và cần thời gian để thích nghi với thay đổi. Khác biệt về nhu cầu xã hội: Chim Công thích môi trường sôi động với nhiều người, trong khi Bồ Câu ưa thích không gian yên tĩnh hơn với ít người hơn.",
            successStrategies = "Chim Công cần phát triển sự kiên nhẫn, đôi khi chậm lại và lắng nghe kỹ càng nhu cầu của Bồ Câu. Bồ Câu cần mở lòng hơn với ý tưởng mới và học cách bày tỏ giới hạn của mình một cách rõ ràng. Xây dựng lịch trình cân bằng giữa thời gian tương tác sôi động và thời gian yên tĩnh để phản ánh. Chim Công có thể giúp Bồ Câu mở rộng vùng an toàn, trong khi Bồ Câu giúp Chim Công phát triển sự kiên trì.",
            workRelationship = "Đây là một trong những sự kết hợp hài hòa nhất. Bồ Câu tạo nền tảng vững chắc và đáng tin cậy cho sự sáng tạo của Chim Công phát triển. Chim Công giúp Bồ Câu mở rộng tầm nhìn và khám phá những khả năng mới. Bồ Câu hỗ trợ Chim Công hoàn thành công việc và duy trì cam kết dài hạn. Cả hai đều quan tâm đến mối quan hệ và hòa hợp, tạo nên môi trường làm việc tích cực và hỗ trợ lẫn nhau."
        ),
        Pair("Chim Công", "Chim Cú") to Compatibility(
            trait1 = "Chim Công",
            trait2 = "Chim Cú",
            compatibilityLevel = "Thấp",
            description = "Chim Công và Chim Cú thể hiện những phương pháp tiếp cận hoàn toàn khác biệt. Chim Công dựa vào cảm xúc, trực giác và giao tiếp cởi mở, trong khi Chim Cú theo đuổi phân tích logic và cấu trúc. Chim Công mang đến sự sáng tạo, nhiệt huyết và kỹ năng xã hội, còn Chim Cú đóng góp sự chính xác, phân tích chuyên sâu và tư duy có hệ thống.",
            challenges = "Chim Công thường thấy Chim Cú quá chi tiết, cứng nhắc và chậm chạp trong quyết định. Ngược lại, Chim Cú xem Chim Công thiếu tổ chức, bốc đồng và không đủ tập trung vào dữ liệu. Chim Công chia sẻ cảm xúc tự do, trong khi Chim Cú thường giữ khoảng cách và tập trung vào công việc. Khác biệt về nhịp độ: Chim Công nhanh và linh hoạt, Chim Cú chậm rãi và có phương pháp.",
            successStrategies = "Chim Công cần phát triển sự kiên nhẫn với quá trình phân tích của Chim Cú và cung cấp không gian cho việc nghiên cứu chi tiết. Chim Cú nên cởi mở hơn với những ý tưởng mới và học cách đánh giá cao năng lượng sáng tạo. Cả hai cần xây dựng giao tiếp rõ ràng về phong cách làm việc và mong đợi của mình. Chim Công nên chuẩn bị dữ liệu hỗ trợ cho ý tưởng, trong khi Chim Cú học cách bày tỏ sự đánh giá cao đối với sự nhiệt tình.",
            workRelationship = "Mối quan hệ này đòi hỏi nỗ lực đáng kể từ cả hai phía, nhưng có thể bổ sung cho nhau một cách hiệu quả nếu được quản lý tốt. Chim Công mang lại sự sáng tạo và kỹ năng giao tiếp xuất sắc, trong khi Chim Cú đảm bảo độ chính xác và phân tích sâu sắc. Chim Công có thể giúp Chim Cú phát triển kỹ năng xã hội và tư duy sáng tạo, trong khi Chim Cú giúp Chim Công trở nên có tổ chức và thực tế hơn."
        ),
        Pair("Bồ Câu", "Bồ Câu") to Compatibility(
            trait1 = "Bồ Câu",
            trait2 = "Bồ Câu",
            compatibilityLevel = "Cao",
            description = "Hai Bồ Câu làm việc cùng nhau tạo nên môi trường hài hòa, ổn định và đầy sự hỗ trợ. Không khí làm việc yên bình, thân thiện và ít xung đột, với sự tôn trọng và thấu hiểu sâu sắc lẫn nhau. Họ cùng đánh giá cao sự nhất quán, tin cậy và quan tâm đến cảm xúc của người khác. Môi trường này tạo cảm giác an toàn và được hỗ trợ cho tất cả mọi người.",
            challenges = "Hai Bồ Câu có thể thiếu sự đổi mới và gặp khó khăn khi thích nghi với thay đổi lớn. Họ thường gặp khó khăn trong việc ra quyết định nhanh chóng và dứt khoát, dễ rơi vào trạng thái do dự kéo dài. Cả hai đều có xu hướng tránh xung đột và những cuộc trò chuyện khó khăn, dẫn đến việc các vấn đề có thể không được giải quyết. Thiếu người đưa ra quyết định mạnh mẽ trong tình huống khẩn cấp.",
            successStrategies = "Hai Bồ Câu cần thúc đẩy nhau bước ra khỏi vùng an toàn và thử nghiệm những cách tiếp cận mới. Đặt ra thời hạn rõ ràng cho việc ra quyết định để tránh sự trì hoãn không cần thiết. Phát triển kỹ năng giải quyết xung đột mang tính xây dựng và học cách đối mặt với những cuộc trò chuyện khó khăn. Tìm kiếm góc nhìn từ những người có tính cách khác để mở rộng tư duy và cách tiếp cận.",
            workRelationship = "Mối quan hệ làm việc giữa hai Bồ Câu rất thoải mái, đáng tin cậy và bền vững lâu dài. Họ xuất sắc trong việc xây dựng quan hệ khách hàng ổn định và cung cấp dịch vụ chất lượng cao. Môi trường làm việc ít căng thẳng với sự hỗ trợ cảm xúc mạnh mẽ. Tuy nhiên, họ sẽ được hưởng lợi từ sự hỗ trợ của những người có tính cách khác để thúc đẩy đổi mới và ra quyết định khi cần thiết."
        ),
        Pair("Bồ Câu", "Chim Cú") to Compatibility(
            trait1 = "Bồ Câu",
            trait2 = "Chim Cú",
            compatibilityLevel = "Cao",
            description = "Bồ Câu và Chim Cú tạo nên sự kết hợp hài hòa giữa sự ổn định cảm xúc và phân tích logic. Cả hai đều đánh giá cao chất lượng, làm việc với nhịp độ thận trọng và ưa thích môi trường có cấu trúc. Bồ Câu mang đến sự ổn định, kiên nhẫn và kỹ năng lắng nghe, trong khi Chim Cú đóng góp phân tích sâu sắc và tư duy chi tiết.",
            challenges = "Cả hai có thể quá thận trọng và phân tích kỹ lưỡng, dẫn đến chậm trễ trong hành động và quyết định. Họ có thể gặp khó khăn khi đối mặt với thay đổi nhanh chóng hoặc tình huống khẩn cấp đòi hỏi phản ứng nhanh. Đôi khi quá tập trung vào chi tiết mà bỏ qua bức tranh tổng thể hoặc cơ hội mới. Cả hai đều có xu hướng tránh rủi ro và có thể bỏ lỡ cơ hội đổi mới.",
            successStrategies = "Thiết lập thời hạn rõ ràng và thúc đẩy nhau khi cần thiết để đảm bảo tiến độ. Phát triển kỹ năng ra quyết định nhanh hơn và xác định khi nào đã có đủ thông tin để tiến hành. Thỉnh thoảng mạo hiểm bước ra khỏi vùng an toàn và thử nghiệm cách tiếp cận mới. Tìm kiếm góc nhìn từ những người có tính cách khác để cân bằng xu hướng thận trọng.",
            workRelationship = "Bồ Câu và Chim Cú tạo nên môi trường làm việc ổn định, đáng tin cậy với chất lượng cao và sự nhất quán. Bồ Câu đảm bảo sự hài hòa trong nhóm trong khi Chim Cú chú trọng đến độ chính xác và chi tiết. Họ có khả năng giải quyết vấn đề hiệu quả với sự kết hợp giữa tư duy phân tích và sự kiên nhẫn. Mối quan hệ này tạo nền tảng vững chắc cho các dự án đòi hỏi sự chính xác, tin cậy và chất lượng cao."
        ),
        Pair("Chim Cú", "Chim Cú") to Compatibility(
            trait1 = "Chim Cú",
            trait2 = "Chim Cú",
            compatibilityLevel = "Cao",
            description = "Hai Chim Cú làm việc cùng nhau tạo nên môi trường chuyên nghiệp, có tổ chức cao với chuẩn mực nghiêm ngặt về chất lượng và độ chính xác. Giao tiếp giữa họ rõ ràng, dựa trên dữ liệu và luôn tập trung vào nhiệm vụ. Cả hai đều đánh giá cao sự hoàn hảo, cấu trúc và quy trình được thiết kế tốt. Họ có khả năng phân tích sâu sắc các vấn đề phức tạp và tìm ra những giải pháp toàn diện.",
            challenges = "Hai Chim Cú có thể quá cầu toàn và dành quá nhiều thời gian phân tích chi tiết dẫn đến việc chậm tiến độ và overthinking . Họ thường gặp khó khăn khi cần đưa ra quyết định nhanh chóng trong điều kiện thiếu thông tin đầy đủ. Có khuynh hướng bỏ qua yếu tố cảm xúc và con người trong quá trình làm việc. Khó thích nghi với thay đổi đột ngột hoặc tình huống thiếu cấu trúc rõ ràng.",
            successStrategies = "Thiết lập thời hạn rõ ràng và mục tiêu cụ thể để tránh phân tích quá mức. Chỉ định một người chịu trách nhiệm đưa ra quyết định cuối cùng khi cần thiết. Chủ động tìm kiếm góc nhìn đa dạng từ những người có tính cách khác để mở rộng cách tiếp cận. Phát triển kỹ năng giao tiếp cảm xúc và học cách đánh giá cao yếu tố con người trong công việc. Định kỳ kiểm tra xem họ có đang mắc kẹt trong chi tiết hay không.",
            workRelationship = "Mối quan hệ làm việc giữa hai Chim Cú cực kỳ hiệu quả khi cần sự tỉ mỉ, chuyên môn sâu và chất lượng cao. Họ hiểu và tôn trọng nhu cầu về không gian và thời gian của nhau để phân tích kỹ lưỡng. Môi trường làm việc của họ yên tĩnh, có tổ chức và tập trung cao độ. Tuy nhiên, họ cần cẩn thận để không trở nên quá khép kín và nên chủ động tìm kiếm góc nhìn đa dạng từ bên ngoài."
        )
    )

    fun getCompatibility(trait1: String, trait2: String): Compatibility? {
        val direct = compatibilityMap[trait1 to trait2]
        if (direct != null) return direct

        val reversed = compatibilityMap[trait2 to trait1]
        if (reversed != null) {
            return reversed.copy(
                trait1 = trait1,
                trait2 = trait2
            )
        }

        return null
    }
}
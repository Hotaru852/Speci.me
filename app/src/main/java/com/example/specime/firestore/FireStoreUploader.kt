package com.example.specime.firestore

import android.annotation.SuppressLint
import com.google.firebase.firestore.FirebaseFirestore

object FirestoreUploader {
    @SuppressLint("DefaultLocale")
    fun uploadQuestions() {
        val db = FirebaseFirestore.getInstance()

        val questions = listOf(
            createQuestion(
                "Cách nào mô tả tốt nhất phong cách làm việc của bạn?",
                createAnswer(
                    "Dominance",
                    "Tôi đảm nhận vai trò lãnh đạo và hướng đến kết quả nhanh chóng."
                ),
                createAnswer(
                    "Influence",
                    "Tôi truyền động lực cho người khác và duy trì bầu không khí tích cực."
                ),
                createAnswer("Steadiness", "Tôi tập trung vào làm việc nhóm và hỗ trợ mọi người."),
                createAnswer(
                    "Conscientiousness",
                    "Tôi đảm bảo sự chính xác và tuân thủ các quy trình."
                )
            ),
            createQuestion(
                "Bạn xử lý hạn chót như thế nào?",
                createAnswer("Dominance", "Tôi thúc đẩy bản thân để hoàn thành trước thời hạn."),
                createAnswer(
                    "Influence",
                    "Tôi xem hạn chót như động lực để duy trì năng lượng cao."
                ),
                createAnswer(
                    "Steadiness",
                    "Tôi lập kế hoạch cẩn thận để hoàn thành mà không bị căng thẳng."
                ),
                createAnswer(
                    "Conscientiousness",
                    "Tôi kiểm tra kỹ lưỡng để đảm bảo mọi thứ đúng trước khi nộp."
                )
            ),
            createQuestion(
                "Phong cách lãnh đạo của bạn là gì?",
                createAnswer(
                    "Dominance", "Trực tiếp và tập trung vào mục tiêu."
                ),
                createAnswer(
                    "Influence", "Lôi cuốn và tạo cảm hứng cho người khác."
                ),
                createAnswer(
                    "Steadiness", "Hỗ trợ và kiên nhẫn."
                ),
                createAnswer(
                    "Conscientiousness", "Phân tích và làm việc có phương pháp."
                )
            ),
            createQuestion(
                "Bạn phản ứng thế nào khi một dự án bị chậm tiến độ?",
                createAnswer(
                    "Dominance", "Tôi kiểm soát và thúc đẩy đội nhóm để bắt kịp tiến độ."
                ),
                createAnswer(
                    "Influence", "Tôi nâng cao tinh thần đội nhóm và khuyến khích họ trở lại đúng hướng."
                ),
                createAnswer(
                    "Steadiness", "Tôi phối hợp với người khác để xử lý sự chậm trễ một cách bình tĩnh."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi đánh giá lại kế hoạch để xác định và khắc phục vấn đề."
                )
            ),
            createQuestion(
                "Điều gì thúc đẩy bạn nhất trong môi trường làm việc?",
                createAnswer(
                    "Dominance", "Đạt được các mục tiêu tham vọng."
                ),
                createAnswer(
                    "Influence", "Được công nhận và có cơ hội giao lưu với mọi người."
                ),
                createAnswer(
                    "Steadiness", "Sự ổn định và một đội nhóm hòa hợp."
                ),
                createAnswer(
                    "Conscientiousness", "Đảm bảo chất lượng và độ chính xác trong công việc."
                )
            ),
            createQuestion(
                "Bạn tiếp cận việc giải quyết vấn đề như thế nào?",
                createAnswer(
                    "Dominance", "Tôi nhanh chóng tìm ra giải pháp hiệu quả nhất."
                ),
                createAnswer(
                    "Influence", "Tôi động não với người khác và nghĩ ra các ý tưởng sáng tạo."
                ),
                createAnswer(
                    "Steadiness", "Tôi tìm kiếm giải pháp mà mọi người đều cảm thấy thoải mái."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi phân tích vấn đề kỹ lưỡng trước khi hành động."
                )
            ),
            createQuestion(
                "Khi giao tiếp, bạn thích phong cách nào nhất?",
                createAnswer(
                    "Dominance", "Trực tiếp và đi thẳng vào vấn đề."
                ),
                createAnswer(
                    "Influence", "Nhiệt tình và đầy sức hút."
                ),
                createAnswer(
                    "Steadiness", "Cân nhắc và hỗ trợ."
                ),
                createAnswer(
                    "Conscientiousness", "Rõ ràng và chi tiết."
                )
            ),
            createQuestion(
                "Bạn xử lý chỉ trích như thế nào?",
                createAnswer(
                    "Dominance", "Tôi đối diện trực tiếp và hành động nếu cần thiết."
                ),
                createAnswer(
                    "Influence", "Tôi thảo luận cởi mở và nhanh chóng vượt qua."
                ),
                createAnswer(
                    "Steadiness", "Tôi suy nghĩ và xem xét cách nó ảnh hưởng đến đội nhóm."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi đánh giá cẩn thận để cải thiện hiệu suất của mình."
                )
            ),
            createQuestion(
                "Cách bạn đưa ra quyết định là gì?",
                createAnswer(
                    "Dominance", "Tôi đưa ra quyết định nhanh chóng và dứt khoát."
                ),
                createAnswer(
                    "Influence", "Tôi tham khảo ý kiến người khác và cân nhắc ý kiến của họ."
                ),
                createAnswer(
                    "Steadiness", "Tôi thích dành thời gian và tránh các rủi ro."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi dựa trên dữ liệu và phân tích kỹ lưỡng trước khi quyết định."
                )
            ),
            createQuestion(
                "Khi bạn gặp trở ngại, bạn thường phản ứng như thế nào?",
                createAnswer(
                    "Dominance", "Tôi cố gắng hơn nữa để vượt qua nó."
                ),
                createAnswer(
                    "Influence", "Tôi giữ thái độ tích cực và khuyến khích người khác."
                ),
                createAnswer(
                    "Steadiness", "Tôi giữ bình tĩnh và điều chỉnh cách tiếp cận của mình."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi phân tích những gì đã xảy ra và lập kế hoạch để tránh nó trong tương lai."
                )
            ),
            createQuestion(
                "Bạn thích làm việc trong đội nhóm như thế nào?",
                createAnswer(
                    "Dominance", "Tôi lãnh đạo và thúc đẩy đội nhóm tiến lên phía trước."
                ),
                createAnswer(
                    "Influence", "Tôi hợp tác và giữ cho đội nhóm tràn đầy năng lượng."
                ),
                createAnswer(
                    "Steadiness", "Tôi hỗ trợ người khác và giúp duy trì sự hòa hợp."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi tập trung vào chi tiết và đảm bảo chất lượng công việc."
                )
            ),
            createQuestion(
                "Bạn xử lý căng thẳng trong công việc như thế nào?",
                createAnswer(
                    "Dominance", "Tôi đối mặt với thử thách trực tiếp và tiếp tục tiến lên."
                ),
                createAnswer(
                    "Influence", "Tôi sử dụng hài hước và suy nghĩ tích cực để giải tỏa căng thẳng."
                ),
                createAnswer(
                    "Steadiness", "Tôi giữ bình tĩnh và dựa vào thói quen để quản lý căng thẳng."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi trở nên tập trung và tổ chức hơn."
                )
            ),
            createQuestion(
                "Cách tiếp cận của bạn đối với sự thay đổi là gì?",
                createAnswer(
                    "Dominance", "Tôi chấp nhận thay đổi và dẫn dắt việc thích nghi."
                ),
                createAnswer(
                    "Influence", "Tôi coi nó là một cơ hội và khuyến khích người khác cùng tham gia."
                ),
                createAnswer(
                    "Steadiness", "Tôi thích thay đổi từ từ, không làm xáo trộn thói quen."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi phân tích tác động và lập kế hoạch một cách cẩn thận."
                )
            ),
            createQuestion(
                "Khi bắt đầu một nhiệm vụ mới, bạn làm gì đầu tiên?",
                createAnswer(
                    "Dominance", "Tôi bắt tay vào làm ngay lập tức."
                ),
                createAnswer(
                    "Influence", "Tôi thu thập ý kiến từ người khác và động não các ý tưởng."
                ),
                createAnswer(
                    "Steadiness", "Tôi chuẩn bị kỹ lưỡng và tiến hành từng bước."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi nghiên cứu và lập kế hoạch trước khi hành động."
                )
            ),
            createQuestion(
                "Bạn thường phản ứng như thế nào với xung đột?",
                createAnswer(
                    "Dominance", "Tôi đối mặt trực tiếp và giải quyết nhanh chóng."
                ),
                createAnswer(
                    "Influence", "Tôi cố gắng làm dịu tình hình và giữ bầu không khí thoải mái."
                ),
                createAnswer(
                    "Steadiness", "Tôi tìm kiếm giải pháp hòa bình làm hài lòng tất cả mọi người."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi phân tích tình hình và tìm giải pháp logic."
                )
            ),
            createQuestion(
                "Điều gì thúc đẩy bạn đạt được thành công?",
                createAnswer(
                    "Dominance", "Khao khát chiến thắng và đạt được kết quả cao nhất."
                ),
                createAnswer(
                    "Influence", "Cơ hội truyền cảm hứng và lãnh đạo người khác."
                ),
                createAnswer(
                    "Steadiness", "Sự hài lòng từ việc đáng tin cậy và đáng tin cậy."
                ),
                createAnswer(
                    "Conscientiousness", "Mong muốn theo đuổi sự xuất sắc và chính xác."
                )
            ),
            createQuestion(
                "Bạn xử lý phản hồi từ người khác như thế nào?",
                createAnswer(
                    "Dominance", "Tôi xem xét và thực hiện thay đổi nếu cần thiết."
                ),
                createAnswer(
                    "Influence", "Tôi đánh giá cao và sử dụng nó để cải thiện cách tiếp cận của mình."
                ),
                createAnswer(
                    "Steadiness", "Tôi coi trọng phản hồi và điều chỉnh để duy trì sự hòa hợp."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi đánh giá cẩn thận và tinh chỉnh công việc của mình."
                )
            ),
            createQuestion(
                "Cách tiếp cận của bạn khi lập kế hoạch là gì?",
                createAnswer(
                    "Dominance", "Tôi đặt ra các mục tiêu đầy tham vọng và vạch ra các bước để đạt được chúng."
                ),
                createAnswer(
                    "Influence", "Tôi tạo ra các kế hoạch linh hoạt cho phép sự tự phát."
                ),
                createAnswer(
                    "Steadiness", "Tôi lập kế hoạch cẩn thận để đảm bảo sự nhất quán."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi phát triển các kế hoạch chi tiết và tuân theo chúng sát sao."
                )
            ),
            createQuestion(
                "Bạn ưu tiên các nhiệm vụ như thế nào?",
                createAnswer(
                    "Dominance", "Tôi giải quyết các nhiệm vụ quan trọng nhất trước tiên."
                ),
                createAnswer(
                    "Influence", "Tôi tập trung vào các nhiệm vụ liên quan đến sự hợp tác."
                ),
                createAnswer(
                    "Steadiness", "Tôi ưu tiên các nhiệm vụ duy trì sự ổn định."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi sắp xếp các nhiệm vụ dựa trên tầm quan trọng logic."
                )
            ),
            createQuestion(
                "Điều gì mô tả đạo đức làm việc của bạn?",
                createAnswer(
                    "Dominance", "Hướng đến kết quả và sự tập trung cao."
                ),
                createAnswer(
                    "Influence", "Năng động và hướng đến con người."
                ),
                createAnswer(
                    "Steadiness", "Đáng tin cậy và nhất quán."
                ),
                createAnswer(
                    "Conscientiousness", "Chú ý đến chi tiết và cẩn thận."
                )
            ),
            createQuestion(
                "Bạn thích được công nhận công việc của mình như thế nào?",
                createAnswer(
                    "Dominance", "Được công khai công nhận những thành tựu."
                ),
                createAnswer(
                    "Influence", "Nhận được phản hồi tích cực và sự công nhận xã hội."
                ),
                createAnswer(
                    "Steadiness", "Sự đánh giá và ghi nhận một cách yên lặng."
                ),
                createAnswer(
                    "Conscientiousness", "Được công nhận về độ chính xác và chất lượng."
                )
            ),
            createQuestion(
                "Bạn tiếp cận việc học kỹ năng mới như thế nào?",
                createAnswer(
                    "Dominance", "Tôi học nhanh qua kinh nghiệm thực tế."
                ),
                createAnswer(
                    "Influence", "Tôi thích học tập tương tác và theo nhóm."
                ),
                createAnswer(
                    "Steadiness", "Tôi áp dụng phương pháp học từ từ và có kế hoạch."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi nghiên cứu kỹ và thực hành cho đến khi hoàn thiện."
                )
            ),
            createQuestion(
                "Phản ứng của bạn với các quy định nghiêm ngặt là gì?",
                createAnswer(
                    "Dominance", "Tôi tuân thủ nếu chúng giúp đạt được mục tiêu."
                ),
                createAnswer(
                    "Influence", "Tôi điều chỉnh chúng phù hợp với tình huống."
                ),
                createAnswer(
                    "Steadiness", "Tôi tuân thủ chúng để duy trì trật tự."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi tôn trọng và tuân thủ chúng sát sao."
                )
            ),
            createQuestion(
                "Bạn quản lý thời gian như thế nào?",
                createAnswer(
                    "Dominance", "Tôi ưu tiên các nhiệm vụ mang lại kết quả lớn nhất."
                ),
                createAnswer(
                    "Influence", "Tôi cân bằng nhiệm vụ với các tương tác xã hội."
                ),
                createAnswer(
                    "Steadiness", "Tôi tuân thủ thói quen để quản lý thời gian hiệu quả."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi lập kế hoạch cẩn thận để đảm bảo sự chính xác và hiệu quả."
                )
            ),
            createQuestion(
                "Cách tiếp cận của bạn khi xử lý rủi ro là gì?",
                createAnswer(
                    "Dominance", "Tôi thực hiện rủi ro có tính toán để đạt được mục tiêu."
                ),
                createAnswer(
                    "Influence", "Tôi cân nhắc rủi ro nếu chúng mang lại cơ hội thú vị."
                ),
                createAnswer(
                    "Steadiness", "Tôi thích tránh rủi ro để duy trì sự ổn định."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi phân tích kỹ lưỡng rủi ro trước khi quyết định."
                )
            ),
            createQuestion(
                "Bạn thích đối mặt với thử thách mới như thế nào trong công việc?",
                createAnswer(
                    "Dominance", "Tôi đảm nhận và giải quyết thử thách trực tiếp."
                ),
                createAnswer(
                    "Influence", "Tôi hợp tác với người khác để tìm giải pháp sáng tạo."
                ),
                createAnswer(
                    "Steadiness", "Tôi tiếp cận một cách cẩn thận, cân nhắc tác động đến người khác."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi phân tích chi tiết thử thách trước khi hành động."
                )
            ),
            createQuestion(
                "Vai trò bạn thường đảm nhận trong các cuộc thảo luận nhóm là gì?",
                createAnswer(
                    "Dominance", "Tôi dẫn dắt cuộc trò chuyện và định hướng trọng tâm."
                ),
                createAnswer(
                    "Influence", "Tôi nhiệt tình đóng góp và khuyến khích sự tham gia."
                ),
                createAnswer(
                    "Steadiness", "Tôi lắng nghe cẩn thận và hỗ trợ khi cần thiết."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi cung cấp những ý kiến thấu đáo dựa trên thực tế."
                )
            ),
            createQuestion(
                "Bạn phản ứng như thế nào trước những thay đổi đột ngột trong dự án?",
                createAnswer(
                    "Dominance", "Tôi thích nghi nhanh chóng và tiếp tục tiến về phía trước."
                ),
                createAnswer(
                    "Influence", "Tôi tìm cách làm cho sự thay đổi trở nên thú vị và tích cực."
                ),
                createAnswer(
                    "Steadiness", "Tôi muốn hiểu lý do đằng sau sự thay đổi và thích nghi từ từ."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi đánh giá cách thay đổi sẽ ảnh hưởng đến kế hoạch tổng thể trước khi thực hiện."
                )
            ),
            createQuestion(
                "Cách tiếp cận của bạn khi giao nhiệm vụ là gì?",
                createAnswer(
                    "Dominance", "Tôi giao nhiệm vụ để đảm bảo công việc được hoàn thành hiệu quả."
                ),
                createAnswer(
                    "Influence", "Tôi khuyến khích người khác tham gia để giữ họ hứng thú và có động lực."
                ),
                createAnswer(
                    "Steadiness", "Tôi giao nhiệm vụ một cách cẩn thận, đảm bảo mọi người cảm thấy thoải mái."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi giao nhiệm vụ cho những người có thể duy trì tiêu chuẩn cao."
                )
            ),
            createQuestion(
                "Bạn thường chuẩn bị thế nào cho các cuộc họp quan trọng?",
                createAnswer(
                    "Dominance", "Tôi phác thảo các điểm chính và mục tiêu để đạt được kết quả."
                ),
                createAnswer(
                    "Influence", "Tôi nghĩ cách thu hút người khác và tạo ra bầu không khí tích cực."
                ),
                createAnswer(
                    "Steadiness", "Tôi chuẩn bị bằng cách xem xét cách giữ cuộc họp diễn ra suôn sẻ và hợp tác."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi thu thập tất cả thông tin cần thiết và chuẩn bị ghi chú chi tiết."
                )
            ),
            createQuestion(
                "Khi gặp hạn chót chặt chẽ, bạn phản ứng như thế nào?",
                createAnswer(
                    "Dominance", "Tôi làm việc cường độ cao để hoàn thành đúng hạn và thúc đẩy kết quả."
                ),
                createAnswer(
                    "Influence", "Tôi giữ tinh thần lạc quan và khuyến khích người khác duy trì đà tiến."
                ),
                createAnswer(
                    "Steadiness", "Tôi lập kế hoạch thời gian cẩn thận để hoàn thành mà không bị căng thẳng."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi tập trung hoàn thành công việc một cách chính xác, ngay cả dưới áp lực."
                )
            ),
            createQuestion(
                "Bạn thích tham gia vào quá trình ra quyết định như thế nào?",
                createAnswer(
                    "Dominance", "Tôi thích dẫn dắt và đưa ra quyết định nhanh chóng."
                ),
                createAnswer(
                    "Influence", "Tôi thích động não và hợp tác với người khác."
                ),
                createAnswer(
                    "Steadiness", "Tôi thích hỗ trợ quy trình bằng cách cung cấp ý kiến ổn định."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi thích cung cấp phân tích chi tiết trước khi đưa ra quyết định."
                )
            ),
            createQuestion(
                "Cách tiếp cận của bạn đối với làm việc nhóm là gì?",
                createAnswer(
                    "Dominance", "Tôi đảm nhận vai trò lãnh đạo để đảm bảo đội nhóm đạt được mục tiêu."
                ),
                createAnswer(
                    "Influence", "Tôi tạo năng lượng cho đội nhóm và giữ mọi người luôn có động lực."
                ),
                createAnswer(
                    "Steadiness", "Tôi khuyến khích sự hợp tác và đảm bảo mọi người đều được tham gia."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi tập trung đảm bảo công việc của đội đạt độ chính xác và chất lượng cao."
                )
            ),
            createQuestion(
                "Bạn xử lý phản hồi về công việc của mình như thế nào?",
                createAnswer(
                    "Dominance", "Tôi cân nhắc và điều chỉnh để cải thiện kết quả."
                ),
                createAnswer(
                    "Influence", "Tôi đánh giá cao phản hồi và sử dụng nó để cải thiện các mối quan hệ của mình."
                ),
                createAnswer(
                    "Steadiness", "Tôi nghiêm túc tiếp nhận phản hồi và thực hiện thay đổi để duy trì sự hòa hợp."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi phân tích kỹ phản hồi để cải thiện độ chính xác."
                )
            ),
            createQuestion(
                "Điều gì bạn đánh giá cao nhất trong môi trường làm việc?",
                createAnswer(
                    "Dominance", "Khả năng đạt được kết quả và đối mặt với thử thách."
                ),
                createAnswer(
                    "Influence", "Bầu không khí tích cực với cơ hội giao tiếp xã hội."
                ),
                createAnswer(
                    "Steadiness", "Môi trường ổn định và hỗ trợ."
                ),
                createAnswer(
                    "Conscientiousness", "Môi trường nơi chất lượng và độ chính xác được ưu tiên."
                )
            ),
            createQuestion(
                "Cách tiếp cận của bạn khi xây dựng mối quan hệ là gì?",
                createAnswer(
                    "Dominance", "Tôi kết nối với những người có thể giúp tôi đạt được mục tiêu."
                ),
                createAnswer(
                    "Influence", "Tôi thích gặp gỡ người mới và xây dựng các mối quan hệ."
                ),
                createAnswer(
                    "Steadiness", "Tôi xây dựng mối quan hệ với những người tôi tin tưởng và có giá trị tương đồng."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi tập trung xây dựng các kết nối chuyên nghiệp phù hợp với chuyên môn của mình."
                )
            ),
            createQuestion(
                "Khi được giới thiệu ý tưởng mới, phản ứng ban đầu của bạn là gì?",
                createAnswer(
                    "Dominance", "Tôi đánh giá nhanh chóng và quyết định liệu nó có đáng theo đuổi hay không."
                ),
                createAnswer(
                    "Influence", "Tôi hào hứng và khám phá tiềm năng của nó cùng người khác."
                ),
                createAnswer(
                    "Steadiness", "Tôi xem xét nó phù hợp như thế nào với các kế hoạch và thói quen hiện tại."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi phân tích tính khả thi và tác động tiềm năng của nó một cách cẩn thận."
                )
            ),
            createQuestion(
                "Bạn tiếp cận việc làm nhiều việc cùng lúc như thế nào?",
                createAnswer(
                    "Dominance", "Tôi xử lý nhiều nhiệm vụ cùng lúc để tối ưu hóa hiệu quả."
                ),
                createAnswer(
                    "Influence", "Tôi xử lý nhiều nhiệm vụ trong khi giữ tinh thần cao và thu hút người khác."
                ),
                createAnswer(
                    "Steadiness", "Tôi thích tập trung vào một nhiệm vụ tại một thời điểm để đảm bảo tính nhất quán."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi tổ chức các nhiệm vụ cẩn thận để đảm bảo mỗi nhiệm vụ được thực hiện đạt tiêu chuẩn cao."
                )
            ),
            createQuestion(
                "Bạn thường đóng vai trò gì trong việc giải quyết xung đột?",
                createAnswer(
                    "Dominance", "Tôi kiểm soát để giải quyết xung đột nhanh chóng."
                ),
                createAnswer(
                    "Influence", "Tôi làm trung gian để giữ bầu không khí tích cực và hiệu quả."
                ),
                createAnswer(
                    "Steadiness", "Tôi đóng vai trò là người làm dịu tình hình để giúp mọi người tìm thấy sự đồng thuận."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi phân tích các vấn đề và đề xuất các giải pháp hợp lý."
                )
            ),
            createQuestion(
                "Bạn cảm thấy thế nào về việc chấp nhận rủi ro trong công việc?",
                createAnswer(
                    "Dominance", "Tôi cảm thấy thoải mái chấp nhận rủi ro nếu nó giúp đạt được thành công lớn hơn."
                ),
                createAnswer(
                    "Influence", "Tôi sẵn sàng chấp nhận rủi ro nếu nó mang lại cơ hội thú vị."
                ),
                createAnswer(
                    "Steadiness", "Tôi thích tránh rủi ro và duy trì sự ổn định."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi đánh giá rủi ro một cách cẩn thận và tiến hành với sự thận trọng."
                )
            ),
            createQuestion(
                "Phương pháp giao tiếp ưa thích của bạn là gì?",
                createAnswer(
                    "Dominance", "Trực tiếp và rõ ràng."
                ),
                createAnswer(
                    "Influence", "Thân thiện và hấp dẫn."
                ),
                createAnswer(
                    "Steadiness", "Bình tĩnh và hỗ trợ."
                ),
                createAnswer(
                    "Conscientiousness", "Rõ ràng và chi tiết."
                )
            ),
            createQuestion(
                "Bạn quản lý dự án như thế nào?",
                createAnswer(
                    "Dominance", "Tôi thúc đẩy dự án tiến lên phía trước, tập trung vào kết quả."
                ),
                createAnswer(
                    "Influence", "Tôi giữ dự án sôi động và khuyến khích làm việc nhóm."
                ),
                createAnswer(
                    "Steadiness", "Tôi đảm bảo dự án diễn ra suôn sẻ và nhất quán."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi quản lý dự án một cách tỉ mỉ, tập trung vào chất lượng."
                )
            ),
            createQuestion(
                "Bạn xử lý công việc lặp đi lặp lại như thế nào?",
                createAnswer(
                    "Dominance", "Tôi hoàn thành nhanh chóng để chuyển sang công việc thử thách hơn."
                ),
                createAnswer(
                    "Influence", "Tôi tìm cách làm cho công việc thú vị hơn hoặc giao việc đó cho người khác."
                ),
                createAnswer(
                    "Steadiness", "Tôi tìm thấy sự thoải mái trong thói quen và thực hiện một cách nhất quán."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi cẩn thận thực hiện công việc lặp lại một cách chính xác mỗi lần."
                )
            ),
            createQuestion(
                "Bạn làm gì khi người khác không đồng ý với ý tưởng của bạn?",
                createAnswer(
                    "Dominance", "Tôi bảo vệ quan điểm của mình một cách mạnh mẽ và thúc đẩy ý tưởng của tôi."
                ),
                createAnswer(
                    "Influence", "Tôi lắng nghe ý kiến người khác và cố gắng kết hợp ý tưởng của họ với của mình."
                ),
                createAnswer(
                    "Steadiness", "Tôi tìm kiếm một thỏa hiệp làm hài lòng tất cả mọi người."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi đánh giá phản hồi và điều chỉnh ý tưởng dựa trên logic."
                )
            ),
            createQuestion(
                "Bạn thích thiết lập mục tiêu như thế nào?",
                createAnswer(
                    "Dominance", "Tôi đặt ra những mục tiêu đầy thử thách và thúc đẩy bản thân để đạt được chúng nhanh chóng."
                ),
                createAnswer(
                    "Influence", "Tôi đặt ra những mục tiêu đầy tham vọng và cũng thú vị để theo đuổi."
                ),
                createAnswer(
                    "Steadiness", "Tôi đặt ra những mục tiêu thực tế và ổn định mà tôi biết mình có thể đạt được."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi đặt ra những mục tiêu chính xác, có thể đo lường và theo dõi tiến độ cẩn thận."
                )
            ),
            createQuestion(
                "Bạn xử lý tình huống thiếu kiểm soát như thế nào?",
                createAnswer(
                    "Dominance", "Tôi cố gắng lấy lại quyền kiểm soát hoặc ảnh hưởng đến tình hình."
                ),
                createAnswer(
                    "Influence", "Tôi thích nghi và cố gắng duy trì cái nhìn tích cực."
                ),
                createAnswer(
                    "Steadiness", "Tôi giữ bình tĩnh và đi theo dòng chảy, tránh căng thẳng."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi tập trung vào những gì tôi có thể kiểm soát và quản lý chi tiết cẩn thận."
                )
            ),
            createQuestion(
                "Bạn thường tiếp cận một nhiệm vụ mới như thế nào?",
                createAnswer(
                    "Dominance", "Tôi bắt đầu ngay lập tức, tập trung vào hoàn thành nhanh chóng."
                ),
                createAnswer(
                    "Influence", "Tôi thu thập ý kiến của người khác và làm cho nhiệm vụ trở nên thú vị."
                ),
                createAnswer(
                    "Steadiness", "Tôi chuẩn bị kỹ lưỡng trước khi bắt đầu."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi lập kế hoạch từng bước trước khi hành động."
                )
            ),
            createQuestion(
                "Phản ứng của bạn đối với phản hồi thách thức công việc của bạn là gì?",
                createAnswer(
                    "Dominance", "Tôi bảo vệ công việc của mình nhưng sẽ thay đổi nếu cần thiết."
                ),
                createAnswer(
                    "Influence", "Tôi cân nhắc phản hồi và làm việc để cải thiện trong khi giữ thái độ tích cực."
                ),
                createAnswer(
                    "Steadiness", "Tôi chấp nhận phản hồi và thực hiện điều chỉnh để tránh xung đột."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi đánh giá cẩn thận phản hồi và tinh chỉnh công việc của mình cho phù hợp."
                )
            ),
            createQuestion(
                "Bạn tiếp cận sự đổi mới trong công việc như thế nào?",
                createAnswer(
                    "Dominance", "Tôi dẫn đầu và thúc đẩy việc thực hiện các ý tưởng mới một cách nhanh chóng."
                ),
                createAnswer(
                    "Influence", "Tôi cảm thấy hào hứng và khuyến khích người khác sáng tạo."
                ),
                createAnswer(
                    "Steadiness", "Tôi hỗ trợ sự đổi mới nhưng thích sự triển khai từ từ."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi đánh giá rủi ro và lợi ích tiềm năng trước khi chấp nhận sự đổi mới."
                )
            ),
            createQuestion(
                "Bạn duy trì năng lượng của mình tại nơi làm việc như thế nào?",
                createAnswer(
                    "Dominance", "Tôi tập trung vào đạt được mục tiêu để duy trì năng lượng."
                ),
                createAnswer(
                    "Influence", "Tôi tương tác với người khác và giữ môi trường sống động."
                ),
                createAnswer(
                    "Steadiness", "Tôi duy trì nhịp độ công việc và nghỉ ngơi đều đặn để giữ sự ổn định."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi tổ chức các nhiệm vụ của mình và quản lý thời gian một cách hiệu quả để tránh kiệt sức."
                )
            ),
            createQuestion(
                "Bạn xử lý các cuộc trò chuyện khó khăn như thế nào?",
                createAnswer(
                    "Dominance", "Tôi giải quyết vấn đề một cách trực tiếp và quyết đoán."
                ),
                createAnswer(
                    "Influence", "Tôi tiếp cận cuộc trò chuyện với sự lạc quan và đồng cảm."
                ),
                createAnswer(
                    "Steadiness", "Tôi cố gắng giữ cuộc trò chuyện bình tĩnh và tìm điểm chung."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi chuẩn bị kỹ lưỡng và sử dụng dữ kiện để hỗ trợ quan điểm của mình."
                )
            ),
            createQuestion(
                "Khi bắt đầu một vai trò mới, ưu tiên của bạn là gì?",
                createAnswer(
                    "Dominance", "Thiết lập quyền lực và đạt được kết quả nhanh chóng."
                ),
                createAnswer(
                    "Influence", "Xây dựng mối quan hệ và tạo môi trường tích cực."
                ),
                createAnswer(
                    "Steadiness", "Hiểu động lực của đội nhóm và hòa nhập một cách suôn sẻ."
                ),
                createAnswer(
                    "Conscientiousness", "Học kỹ các quy tắc, quy trình và kỳ vọng."
                )
            ),
            createQuestion(
                "Bạn phản ứng như thế nào khi ai đó không đồng ý với bạn?",
                createAnswer(
                    "Dominance", "Tôi bảo vệ lập trường của mình một cách kiên quyết và thúc đẩy quan điểm của mình."
                ),
                createAnswer(
                    "Influence", "Tôi cố gắng hiểu quan điểm của họ và tìm kiếm điểm chung."
                ),
                createAnswer(
                    "Steadiness", "Tôi lắng nghe cẩn thận và tìm cách đáp ứng cả hai bên."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi xem xét lập luận của họ và đánh giá một cách logic."
                )
            ),
            createQuestion(
                "Bạn ưu tiên điều gì trong các nhiệm vụ hàng ngày của mình?",
                createAnswer(
                    "Dominance", "Hoàn thành các nhiệm vụ có tác động lớn nhất đến mục tiêu."
                ),
                createAnswer(
                    "Influence", "Làm việc trên các nhiệm vụ liên quan đến người khác và giữ cho ngày năng động."
                ),
                createAnswer(
                    "Steadiness", "Đảm bảo các nhiệm vụ được thực hiện một cách nhất quán và đáng tin cậy."
                ),
                createAnswer(
                    "Conscientiousness", "Tập trung vào các nhiệm vụ đòi hỏi độ chính xác và chú ý đến chi tiết."
                )
            ),
            createQuestion(
                "Bạn phản ứng như thế nào với các hạn chót nghiêm ngặt?",
                createAnswer(
                    "Dominance", "Tôi thúc đẩy bản thân và người khác để đáp ứng hoặc vượt qua hạn chót."
                ),
                createAnswer(
                    "Influence", "Tôi giữ thái độ tích cực và động viên đội nhóm hoàn thành đúng hạn."
                ),
                createAnswer(
                    "Steadiness", "Tôi lên kế hoạch công việc cẩn thận để đáp ứng hạn chót mà không căng thẳng."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi tập trung vào độ chính xác, đảm bảo mọi thứ được thực hiện đúng ngay cả dưới áp lực thời gian."
                )
            ),
            createQuestion(
                "Khi được giao một vấn đề phức tạp, bạn tiếp cận nó như thế nào?",
                createAnswer(
                    "Dominance", "Tôi nhanh chóng phân tích và tập trung giải quyết các phần quan trọng nhất."
                ),
                createAnswer(
                    "Influence", "Tôi thu thập ý kiến từ người khác và động não các giải pháp sáng tạo."
                ),
                createAnswer(
                    "Steadiness", "Tôi dành thời gian để hiểu đầy đủ vấn đề trước khi hành động."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi phân tích cẩn thận mọi khía cạnh và phát triển một kế hoạch chi tiết."
                )
            ),
            createQuestion(
                "Bạn quản lý làm việc trong môi trường áp lực cao như thế nào?",
                createAnswer(
                    "Dominance", "Tôi phát triển mạnh dưới áp lực và sử dụng nó để thúc đẩy hiệu suất."
                ),
                createAnswer(
                    "Influence", "Tôi giữ mọi thứ nhẹ nhàng và khuyến khích mọi người duy trì thái độ tích cực."
                ),
                createAnswer(
                    "Steadiness", "Tôi dựa vào thói quen và sự nhất quán để quản lý căng thẳng."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi giữ tổ chức và tập trung vào duy trì tiêu chuẩn cao."
                )
            ),
            createQuestion(
                "Bạn thích nhận hướng dẫn như thế nào?",
                createAnswer(
                    "Dominance", "Ngắn gọn và đi thẳng vào vấn đề để tôi có thể bắt đầu ngay."
                ),
                createAnswer(
                    "Influence", "Với sự nhiệt tình, cho phép linh hoạt và sáng tạo."
                ),
                createAnswer(
                    "Steadiness", "Rõ ràng và có cơ hội đặt câu hỏi để làm rõ."
                ),
                createAnswer(
                    "Conscientiousness", "Chi tiết, với tất cả thông tin cần thiết được cung cấp."
                )
            ),
            createQuestion(
                "Bạn đảm bảo công việc của mình phù hợp với mục tiêu tổ chức như thế nào?",
                createAnswer(
                    "Dominance", "Tôi tập trung vào kết quả ảnh hưởng trực tiếp đến mục tiêu của tổ chức."
                ),
                createAnswer(
                    "Influence", "Tôi liên kết công việc của mình với mục tiêu của đội nhóm và giữ mọi người có động lực."
                ),
                createAnswer(
                    "Steadiness", "Tôi đảm bảo công việc của mình hỗ trợ sự ổn định và chức năng tổng thể của đội nhóm."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi tuân thủ chặt chẽ các hướng dẫn và đảm bảo độ chính xác trong đóng góp của mình."
                )
            ),
            createQuestion(
                "Bạn thường tiếp cận việc giải quyết vấn đề với người khác như thế nào?",
                createAnswer(
                    "Dominance", "Tôi dẫn dắt và hướng nhóm tới một giải pháp."
                ),
                createAnswer(
                    "Influence", "Tôi tạo điều kiện thảo luận mở và khuyến khích tư duy sáng tạo."
                ),
                createAnswer(
                    "Steadiness", "Tôi lắng nghe ý kiến của mọi người và tìm kiếm sự đồng thuận."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi cung cấp những hiểu biết dựa trên dữ liệu và đề xuất cách tiếp cận có phương pháp."
                )
            ),
            createQuestion(
                "Phản ứng của bạn khi ai đó làm gián đoạn công việc của bạn là gì?",
                createAnswer(
                    "Dominance", "Tôi giải quyết sự gián đoạn nhanh chóng và trở lại công việc."
                ),
                createAnswer(
                    "Influence", "Tôi chào đón sự gián đoạn nếu nó dẫn đến ý tưởng mới hoặc sự hợp tác."
                ),
                createAnswer(
                    "Steadiness", "Tôi tạm dừng công việc để lắng nghe, sau đó trở lại một cách bình tĩnh."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi thích không bị gián đoạn nhưng xử lý bằng cách tổ chức lại lịch trình của mình."
                )
            ),
            createQuestion(
                "Bạn xử lý sự không chắc chắn trong dự án như thế nào?",
                createAnswer(
                    "Dominance", "Tôi kiểm soát và đưa ra quyết định để giảm sự không chắc chắn."
                ),
                createAnswer(
                    "Influence", "Tôi duy trì thái độ lạc quan và khuyến khích sự linh hoạt trong đội nhóm."
                ),
                createAnswer(
                    "Steadiness", "Tôi tìm kiếm sự rõ ràng và thích chờ đợi cho đến khi có thêm thông tin."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi thu thập càng nhiều dữ liệu càng tốt để giảm thiểu sự không chắc chắn."
                )
            ),
            createQuestion(
                "Bạn xem xét điều gì khi đưa ra quyết định?",
                createAnswer(
                    "Dominance", "Tiềm năng đạt được kết quả tốt nhất có thể."
                ),
                createAnswer(
                    "Influence", "Tác động đến con người và các mối quan hệ."
                ),
                createAnswer(
                    "Steadiness", "Ảnh hưởng đến sự ổn định và nhất quán lâu dài."
                ),
                createAnswer(
                    "Conscientiousness", "Dữ liệu, chi tiết và các tác động logic."
                )
            ),
            createQuestion(
                "Bạn xử lý tình huống cần thay đổi kế hoạch như thế nào?",
                createAnswer(
                    "Dominance", "Tôi điều chỉnh nhanh chóng và tập trung đạt kết quả với kế hoạch mới."
                ),
                createAnswer(
                    "Influence", "Tôi thích nghi và tìm cách giữ cho tình huống tích cực và thú vị."
                ),
                createAnswer(
                    "Steadiness", "Tôi thay đổi dần dần và đảm bảo mọi người cảm thấy thoải mái với hướng đi mới."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi đánh giá cẩn thận kế hoạch mới và thực hiện thay đổi một cách tỉ mỉ."
                )
            ),
            createQuestion(
                "Bạn phản ứng như thế nào khi công việc của mình bị chỉ trích?",
                createAnswer(
                    "Dominance", "Tôi bảo vệ công việc của mình nhưng sẽ thay đổi nếu cần để đạt được mục tiêu."
                ),
                createAnswer(
                    "Influence", "Tôi tiếp nhận và tìm cách cải thiện trong khi giữ thái độ tích cực."
                ),
                createAnswer(
                    "Steadiness", "Tôi nghiêm túc tiếp nhận phản hồi và điều chỉnh để duy trì sự hòa hợp."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi phân tích kỹ lưỡng lời phê bình và cải thiện để đảm bảo độ chính xác."
                )
            ),
            createQuestion(
                "Bạn quản lý cân bằng giữa công việc và cuộc sống như thế nào?",
                createAnswer(
                    "Dominance", "Tôi ưu tiên thời gian để tối đa hóa năng suất và giảm thiểu thời gian chết."
                ),
                createAnswer(
                    "Influence", "Tôi kết hợp các hoạt động xã hội vào công việc để giữ mọi thứ cân bằng."
                ),
                createAnswer(
                    "Steadiness", "Tôi duy trì thói quen ổn định cân bằng giữa công việc và cuộc sống cá nhân."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi tổ chức lịch trình để đảm bảo cả công việc và cuộc sống cá nhân được cấu trúc tốt."
                )
            ),
            createQuestion(
                "Bạn tiếp cận việc học từ sai lầm như thế nào?",
                createAnswer(
                    "Dominance", "Tôi tiến lên nhanh chóng, sử dụng kinh nghiệm để thúc đẩy."
                ),
                createAnswer(
                    "Influence", "Tôi suy ngẫm về kinh nghiệm và chia sẻ với người khác để cùng học hỏi."
                ),
                createAnswer(
                    "Steadiness", "Tôi học từ sai lầm và điều chỉnh để tránh lặp lại."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi phân tích những gì đã sai và thực hiện thay đổi để ngăn chặn lỗi trong tương lai."
                )
            ),
            createQuestion(
                "Cách tiếp cận của bạn khi đặt mục tiêu là gì?",
                createAnswer(
                    "Dominance", "Tôi đặt ra các mục tiêu tham vọng và nỗ lực mạnh mẽ để đạt được chúng."
                ),
                createAnswer(
                    "Influence", "Tôi đặt mục tiêu đầy thử thách nhưng cũng cho phép sự sáng tạo."
                ),
                createAnswer(
                    "Steadiness", "Tôi đặt mục tiêu thực tế và có thể đạt được để đảm bảo tiến bộ ổn định."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi đặt mục tiêu chính xác, chi tiết và lập kế hoạch để đạt được chúng."
                )
            ),
            createQuestion(
                "Bạn thường phản ứng như thế nào với việc quản lý vi mô?",
                createAnswer(
                    "Dominance", "Tôi thấy khó chịu và thích làm việc độc lập."
                ),
                createAnswer(
                    "Influence", "Tôi tập trung vào duy trì mối quan hệ tích cực với quản lý."
                ),
                createAnswer(
                    "Steadiness", "Tôi chấp nhận nó, miễn là nó giúp duy trì sự ổn định."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi đánh giá cao hướng dẫn nếu nó dẫn đến độ chính xác và kết quả tốt hơn."
                )
            ),
            createQuestion(
                "Bạn quản lý khối lượng công việc của mình như thế nào?",
                createAnswer(
                    "Dominance", "Tôi ưu tiên các nhiệm vụ có tác động lớn nhất."
                ),
                createAnswer(
                    "Influence", "Tôi cân bằng khối lượng công việc với cơ hội tương tác với người khác."
                ),
                createAnswer(
                    "Steadiness", "Tôi làm việc ổn định, đảm bảo các nhiệm vụ được hoàn thành mà không vội vàng."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi tổ chức khối lượng công việc một cách tỉ mỉ để duy trì tiêu chuẩn cao."
                )
            ),
            createQuestion(
                "Bạn tiếp cận dịch vụ khách hàng như thế nào?",
                createAnswer(
                    "Dominance", "Tôi tập trung giải quyết vấn đề một cách nhanh chóng và hiệu quả."
                ),
                createAnswer(
                    "Influence", "Tôi đảm bảo khách hàng cảm thấy được lắng nghe và trân trọng."
                ),
                createAnswer(
                    "Steadiness", "Tôi hướng đến cung cấp dịch vụ nhất quán và đáng tin cậy."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi đảm bảo mọi chi tiết đều chính xác và khách hàng nhận được thông tin chính xác."
                )
            ),
            createQuestion(
                "Bạn đối mặt với thất bại như thế nào?",
                createAnswer(
                    "Dominance", "Tôi xem thất bại là một trở ngại tạm thời và nhanh chóng tập trung lại vào thành công."
                ),
                createAnswer(
                    "Influence", "Tôi giữ thái độ lạc quan và tìm kiếm bài học từ thất bại."
                ),
                createAnswer(
                    "Steadiness", "Tôi suy ngẫm về thất bại và thực hiện các bước để đảm bảo nó không xảy ra lần nữa."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi phân tích những gì đã sai và xây dựng kế hoạch để ngăn ngừa thất bại trong tương lai."
                )
            ),
            createQuestion(
                "Bạn thích xử lý xung đột trong môi trường làm việc như thế nào?",
                createAnswer(
                    "Dominance", "Tôi giải quyết trực tiếp và tìm cách giải quyết nhanh chóng."
                ),
                createAnswer(
                    "Influence", "Tôi làm trung gian để tìm giải pháp làm hài lòng mọi người."
                ),
                createAnswer(
                    "Steadiness", "Tôi cố gắng tránh xung đột và duy trì môi trường yên bình."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi tiếp cận xung đột một cách logic và nhằm giải quyết nó một cách công bằng."
                )
            ),
            createQuestion(
                "Điều gì thúc đẩy bạn để thành công?",
                createAnswer(
                    "Dominance", "Khát vọng đạt được và tạo ra ảnh hưởng đáng kể."
                ),
                createAnswer(
                    "Influence", "Niềm phấn khích từ cơ hội mới và sự công nhận."
                ),
                createAnswer(
                    "Steadiness", "Sự hài lòng từ việc làm tốt và giúp đỡ người khác."
                ),
                createAnswer(
                    "Conscientiousness", "Sự theo đuổi sự xuất sắc và độ chính xác trong mọi nhiệm vụ."
                )
            ),
            createQuestion(
                "Bạn thích tổ chức không gian làm việc của mình như thế nào?",
                createAnswer(
                    "Dominance", "Tôi giữ nó chức năng, với mọi thứ tôi cần để hành động nhanh chóng."
                ),
                createAnswer(
                    "Influence", "Tôi giữ nó sống động và đầy những thứ khơi gợi sự sáng tạo."
                ),
                createAnswer(
                    "Steadiness", "Tôi giữ nó gọn gàng và ngăn nắp, với mọi thứ ở đúng vị trí."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi giữ nó được tổ chức tỉ mỉ, với các hệ thống chi tiết cho mọi thứ."
                )
            ),
            createQuestion(
                "Bạn thường xử lý một dự án mới như thế nào?",
                createAnswer(
                    "Dominance", "Tôi nhanh chóng bắt tay vào, tập trung vào việc đạt được kết quả."
                ),
                createAnswer(
                    "Influence", "Tôi tập hợp đội nhóm, đảm bảo mọi người đều hào hứng và tham gia."
                ),
                createAnswer(
                    "Steadiness", "Tôi dành thời gian lên kế hoạch và đảm bảo dự án được tổ chức tốt."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi phân tích yêu cầu của dự án một cách chi tiết trước khi bắt đầu."
                )
            ),
            createQuestion(
                "Bạn cảm nhận thế nào về việc lập kế hoạch dài hạn?",
                createAnswer(
                    "Dominance", "Tôi thích tập trung vào kết quả ngay lập tức và điều chỉnh khi cần."
                ),
                createAnswer(
                    "Influence", "Tôi thích lập kế hoạch nếu nó liên quan đến sự hợp tác và sáng tạo."
                ),
                createAnswer(
                    "Steadiness", "Tôi coi trọng lập kế hoạch dài hạn vì sự ổn định mà nó mang lại."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi thích các kế hoạch dài hạn chi tiết, tính đến mọi biến số."
                )
            ),
            createQuestion(
                "Bạn tiếp cận việc đưa ra hướng dẫn cho người khác như thế nào?",
                createAnswer(
                    "Dominance", "Tôi đưa ra các hướng dẫn rõ ràng, trực tiếp để đảm bảo công việc được hoàn thành."
                ),
                createAnswer(
                    "Influence", "Tôi giải thích hướng dẫn một cách thân thiện và làm cho nó hấp dẫn."
                ),
                createAnswer(
                    "Steadiness", "Tôi cung cấp hướng dẫn một cách kiên nhẫn, đảm bảo mọi người hiểu."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi đưa ra hướng dẫn chi tiết và kỹ lưỡng để tránh bất kỳ sai sót nào."
                )
            ),
            createQuestion(
                "Điều gì thúc đẩy bạn trong một môi trường làm việc nhóm?",
                createAnswer(
                    "Dominance", "Đạt được mục tiêu nhóm và được công nhận là người lãnh đạo."
                ),
                createAnswer(
                    "Influence", "Xây dựng mối quan hệ mạnh mẽ và tận hưởng sự năng động của nhóm."
                ),
                createAnswer(
                    "Steadiness", "Đóng góp vào một môi trường nhóm ổn định và hài hòa."
                ),
                createAnswer(
                    "Conscientiousness", "Đảm bảo công việc của nhóm đạt chất lượng cao và đáp ứng tiêu chuẩn."
                )
            ),
            createQuestion(
                "Bạn xử lý như thế nào khi được giao một nhiệm vụ mà bạn không đồng ý?",
                createAnswer(
                    "Dominance", "Tôi sẽ hoàn thành nó nhưng có thể thách thức quyết định nếu nó ảnh hưởng đến kết quả."
                ),
                createAnswer(
                    "Influence", "Tôi sẽ tìm cách làm cho nhiệm vụ thú vị hơn hoặc hợp tác hơn."
                ),
                createAnswer(
                    "Steadiness", "Tôi sẽ thực hiện nhiệm vụ hết khả năng của mình, tránh xung đột."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi sẽ hoàn thành nhiệm vụ một cách chính xác, nhưng có thể cung cấp phản hồi về lý do tôi không đồng ý."
                )
            ),
            createQuestion(
                "Phương pháp ưa thích của bạn để xử lý căng thẳng là gì?",
                createAnswer(
                    "Dominance", "Tôi chuyển căng thẳng thành hành động, vượt qua nó."
                ),
                createAnswer(
                    "Influence", "Tôi nói chuyện với người khác và giữ thái độ tích cực."
                ),
                createAnswer(
                    "Steadiness", "Tôi quản lý căng thẳng bằng cách tuân thủ các thói quen và giữ bình tĩnh."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi giảm căng thẳng bằng cách tổ chức các nhiệm vụ của mình và đảm bảo mọi thứ được sắp xếp."
                )
            ),
            createQuestion(
                "Bạn thích giao tiếp với đồng nghiệp như thế nào?",
                createAnswer(
                    "Dominance", "Trực tiếp và ngắn gọn, tập trung vào những điều cần thiết."
                ),
                createAnswer(
                    "Influence", "Thân thiện và cởi mở, giữ cho cuộc trò chuyện sôi động."
                ),
                createAnswer(
                    "Steadiness", "Bình tĩnh và chu đáo, đảm bảo mọi người được lắng nghe."
                ),
                createAnswer(
                    "Conscientiousness", "Rõ ràng và trang trọng, cung cấp mọi chi tiết cần thiết."
                )
            ),
            createQuestion(
                "Bạn phản ứng thế nào với những yêu cầu đột ngột về thời gian của mình?",
                createAnswer(
                    "Dominance", "Tôi ưu tiên nhanh chóng và giải quyết các nhiệm vụ quan trọng nhất trước."
                ),
                createAnswer(
                    "Influence", "Tôi giữ thái độ linh hoạt và cố gắng xử lý yêu cầu với sự hào hứng."
                ),
                createAnswer(
                    "Steadiness", "Tôi quản lý yêu cầu một cách bình tĩnh, tuân thủ quy trình làm việc thông thường của mình."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi đánh giá yêu cầu cẩn thận, đảm bảo không làm giảm chất lượng công việc."
                )
            ),
            createQuestion(
                "Bạn tiếp cận việc học kỹ năng mới như thế nào?",
                createAnswer(
                    "Dominance", "Tôi tập trung học nhanh kỹ năng một cách hiệu quả."
                ),
                createAnswer(
                    "Influence", "Tôi tận hưởng quá trình và thường tham gia cùng người khác trong việc học."
                ),
                createAnswer(
                    "Steadiness", "Tôi học tập đều đặn, dành thời gian để hiểu đầy đủ kỹ năng."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi nghiên cứu kỹ lưỡng kỹ năng, đảm bảo hiểu rõ mọi chi tiết."
                )
            ),
            createQuestion(
                "Bạn xử lý công việc lặp đi lặp lại như thế nào?",
                createAnswer(
                    "Dominance", "Tôi hoàn thành chúng nhanh chóng để có thể chuyển sang nhiệm vụ thử thách hơn."
                ),
                createAnswer(
                    "Influence", "Tôi cố gắng làm cho nó thú vị hơn hoặc tìm cách để người khác tham gia."
                ),
                createAnswer(
                    "Steadiness", "Tôi thực hiện với sự kiên nhẫn, duy trì sự nhất quán."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi tập trung vào việc làm đúng hoàn toàn mỗi lần, duy trì tiêu chuẩn cao."
                )
            ),
            createQuestion(
                "Bạn cân nhắc điều gì trước khi đưa ra quyết định lớn?",
                createAnswer(
                    "Dominance", "Ảnh hưởng tiềm năng và tốc độ thực hiện."
                ),
                createAnswer(
                    "Influence", "Cách nó sẽ ảnh hưởng đến mọi người và tinh thần chung."
                ),
                createAnswer(
                    "Steadiness", "Sự ổn định lâu dài và tác động lên đội nhóm."
                ),
                createAnswer(
                    "Conscientiousness", "Các chi tiết, dữ liệu và rủi ro tiềm năng."
                )
            ),
            createQuestion(
                "Bạn đối phó với sự trì hoãn như thế nào?",
                createAnswer(
                    "Dominance", "Tôi thúc đẩy bản thân bắt đầu nhiệm vụ và hoàn thành nó nhanh chóng."
                ),
                createAnswer(
                    "Influence", "Tôi tự động viên bằng cách tập trung vào lợi ích của việc hoàn thành nhiệm vụ."
                ),
                createAnswer(
                    "Steadiness", "Tôi lên lịch thời gian cho nhiệm vụ và tuân thủ kế hoạch."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi chia nhỏ nhiệm vụ thành các bước nhỏ để làm cho nó dễ quản lý hơn."
                )
            ),
            createQuestion(
                "Bạn thường tiếp cận một nhóm mới như thế nào?",
                createAnswer(
                    "Dominance", "Tôi nhanh chóng xác lập vai trò của mình và tập trung vào thúc đẩy nhóm đạt được mục tiêu."
                ),
                createAnswer(
                    "Influence", "Tôi tập trung vào xây dựng mối quan hệ và tạo ra bầu không khí tích cực."
                ),
                createAnswer(
                    "Steadiness", "Tôi dành thời gian quan sát và hiểu động lực của nhóm."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi học các quy trình và tiêu chuẩn của nhóm trước khi tham gia hoàn toàn."
                )
            ),
            createQuestion(
                "Bạn phản ứng như thế nào khi người khác không hoàn thành trách nhiệm của mình?",
                createAnswer(
                    "Dominance", "Tôi đối mặt trực tiếp với họ và thúc đẩy họ cải thiện hiệu suất."
                ),
                createAnswer(
                    "Influence", "Tôi khuyến khích họ và cố gắng tạo động lực một cách tích cực."
                ),
                createAnswer(
                    "Steadiness", "Tôi cung cấp sự hỗ trợ và xem cách tôi có thể giúp họ cải thiện."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi giải quyết vấn đề bằng cách thảo luận về tầm quan trọng của việc đáp ứng tiêu chuẩn."
                )
            ),
            createQuestion(
                "Bạn làm gì khi cảm thấy bị quá tải bởi công việc?",
                createAnswer(
                    "Dominance", "Tôi ưu tiên những nhiệm vụ quan trọng nhất và hoàn thành chúng một cách quyết đoán."
                ),
                createAnswer(
                    "Influence", "Tôi liên hệ với người khác để được hỗ trợ và cố gắng duy trì thái độ tích cực."
                ),
                createAnswer(
                    "Steadiness", "Tôi lùi lại một bước, sắp xếp các nhiệm vụ và giải quyết từng nhiệm vụ một."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi tạo ra một kế hoạch chi tiết để quản lý khối lượng công việc một cách hiệu quả."
                )
            ),
            createQuestion(
                "Bạn xử lý như thế nào khi được yêu cầu đảm nhận thêm trách nhiệm?",
                createAnswer(
                    "Dominance", "Tôi chấp nhận thử thách và tập trung vào đạt được kết quả."
                ),
                createAnswer(
                    "Influence", "Tôi coi đó là cơ hội để kết nối với người khác và phát triển bản thân."
                ),
                createAnswer(
                    "Steadiness", "Tôi đánh giá xem liệu tôi có thể quản lý được mà không ảnh hưởng đến các nhiệm vụ hiện tại hay không."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi cân nhắc các hệ quả và đảm bảo tôi có thể duy trì chất lượng."
                )
            ),
            createQuestion(
                "Bạn thường phản ứng như thế nào với một chính sách hoặc quy trình mới?",
                createAnswer(
                    "Dominance", "Tôi nhanh chóng thích nghi nếu nó giúp đạt được mục tiêu, nếu không, tôi có thể thách thức nó."
                ),
                createAnswer(
                    "Influence", "Tôi tìm kiếm các khía cạnh tích cực và cố gắng thuyết phục người khác đồng hành."
                ),
                createAnswer(
                    "Steadiness", "Tôi điều chỉnh từ từ, đảm bảo nó không làm xáo trộn thói quen của tôi quá nhiều."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi nghiên cứu kỹ chính sách để hiểu cách thực hiện đúng."
                )
            ),
            createQuestion(
                "Bạn tiếp cận việc đưa ra phản hồi như thế nào?",
                createAnswer(
                    "Dominance", "Thẳng thắn và trung thực, tập trung vào cải thiện."
                ),
                createAnswer(
                    "Influence", "Tích cực, đảm bảo khuyến khích nhiều như chỉ trích."
                ),
                createAnswer(
                    "Steadiness", "Nhẹ nhàng, tập trung vào duy trì sự hòa hợp."
                ),
                createAnswer(
                    "Conscientiousness", "Chi tiết, cung cấp quan sát và gợi ý cụ thể."
                )
            ),
            createQuestion(
                "Vai trò của bạn trong việc thúc đẩy người khác là gì?",
                createAnswer(
                    "Dominance", "Tôi thúc đẩy họ đạt được và đặt kỳ vọng cao."
                ),
                createAnswer(
                    "Influence", "Tôi truyền cảm hứng và khuyến khích họ với sự nhiệt tình."
                ),
                createAnswer(
                    "Steadiness", "Tôi hỗ trợ họ một cách âm thầm, cung cấp sự khuyến khích nhất quán."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi thúc đẩy bằng cách làm gương về công việc chất lượng cao."
                )
            ),
            createQuestion(
                "Bạn phản ứng như thế nào khi mọi việc không theo kế hoạch?",
                createAnswer(
                    "Dominance", "Tôi nhanh chóng điều chỉnh và kiểm soát tình hình."
                ),
                createAnswer(
                    "Influence", "Tôi giữ thái độ tích cực và tìm kiếm giải pháp sáng tạo."
                ),
                createAnswer(
                    "Steadiness", "Tôi giữ bình tĩnh và cố gắng đưa mọi thứ trở lại đúng hướng một cách ổn định."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi phân tích những gì đã xảy ra và điều chỉnh kế hoạch một cách cẩn thận."
                )
            ),
            createQuestion(
                "Bạn xử lý như thế nào khi làm việc với một người có phong cách rất khác bạn?",
                createAnswer(
                    "Dominance", "Tôi khẳng định cách tiếp cận của mình nhưng sẵn sàng thỏa hiệp nếu nó dẫn đến kết quả."
                ),
                createAnswer(
                    "Influence", "Tôi thích nghi và tìm kiếm điểm chung để làm cho sự hợp tác thú vị."
                ),
                createAnswer(
                    "Steadiness", "Tôi tập trung tìm cách làm việc cùng nhau một cách hài hòa."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi tôn trọng phong cách của họ nhưng vẫn duy trì tiêu chuẩn chất lượng của mình."
                )
            ),
            createQuestion(
                "Bạn quản lý phản hồi từ nhiều nguồn như thế nào?",
                createAnswer(
                    "Dominance", "Tôi lấy những gì hữu ích và bỏ qua phần còn lại, tập trung vào hành động."
                ),
                createAnswer(
                    "Influence", "Tôi lắng nghe tất cả phản hồi, tìm cách cải thiện và hợp tác."
                ),
                createAnswer(
                    "Steadiness", "Tôi xem xét cẩn thận từng phản hồi và tích hợp nó một cách ổn định."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi phân tích kỹ lưỡng tất cả phản hồi, áp dụng nó khi nó cải thiện độ chính xác."
                )
            ),
            createQuestion(
                "Bạn phản ứng như thế nào với những thách thức bất ngờ?",
                createAnswer(
                    "Dominance", "Tôi nắm quyền và tìm cách vượt qua thử thách một cách nhanh chóng."
                ),
                createAnswer(
                    "Influence", "Tôi giữ thái độ vui vẻ và kêu gọi người khác cùng giải quyết thử thách."
                ),
                createAnswer(
                    "Steadiness", "Tôi tiếp cận thử thách một cách bình tĩnh và làm việc qua nó một cách có phương pháp."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi giải quyết thử thách bằng cách đánh giá tất cả chi tiết và lập kế hoạch cẩn thận."
                )
            ),
            createQuestion(
                "Bạn thích ăn mừng thành tích như thế nào?",
                createAnswer(
                    "Dominance", "Một cách riêng tư, tập trung vào thử thách tiếp theo."
                ),
                createAnswer(
                    "Influence", "Công khai, chia sẻ thành công với mọi người và tận hưởng khoảnh khắc."
                ),
                createAnswer(
                    "Steadiness", "Một cách yên lặng, tập trung vào việc suy ngẫm về thành tích."
                ),
                createAnswer(
                    "Conscientiousness", "Chi tiết, công nhận mọi nỗ lực và công sức đã bỏ ra."
                )
            ),
            createQuestion(
                "Bạn xử lý nhu cầu thay đổi trong môi trường làm việc của mình như thế nào?",
                createAnswer(
                    "Dominance", "Tôi chấp nhận nếu nó dẫn đến kết quả tốt hơn và thúc đẩy sự thay đổi."
                ),
                createAnswer(
                    "Influence", "Tôi giữ thái độ tích cực và thích nghi bằng cách kêu gọi sự tham gia của người khác để dễ dàng chuyển đổi."
                ),
                createAnswer(
                    "Steadiness", "Tôi thực hiện từ từ và đảm bảo sự thay đổi không làm xáo trộn sự ổn định."
                ),
                createAnswer(
                    "Conscientiousness", "Tôi đánh giá chi tiết sự thay đổi và điều chỉnh các quy trình để duy trì tiêu chuẩn cao."
                )
            ),
        )

        for (i in questions.indices) {
            val docId = java.lang.String.format("question_%03d", (i + 1))
            db.collection("questions").document(docId)
                .set(questions[i])
                .addOnSuccessListener {
                    println(
                        "Uploaded: $docId"
                    )
                }
                .addOnFailureListener { e: Exception -> System.err.println("Error uploading " + docId + ": " + e.message) }
        }
    }

    private fun createQuestion(
        question: String,
        vararg answers: Map<String, String>
    ): Map<String, Any> {
        val map: MutableMap<String, Any> = HashMap()
        map["question"] = question
        map["answers"] = listOf(*answers)
        return map
    }

    private fun createAnswer(label: String, description: String): Map<String, String> {
        val map: MutableMap<String, String> = HashMap()
        map["label"] = label
        map["description"] = description
        return map
    }
}
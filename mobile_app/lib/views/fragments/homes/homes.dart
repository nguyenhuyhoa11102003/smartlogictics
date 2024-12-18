import 'dart:async';

import 'package:flutter/material.dart';
import 'package:mobile_app/views/fragments/homes/detail_scan_qr.dart';

class HomeView extends StatefulWidget {
  const HomeView({super.key});

  @override
  State<HomeView> createState() => _HomeViewState();
}

class _HomeViewState extends State<HomeView> {

  late PageController _pageController;
  int _currentPage = 0;
  final List<String> _banners = [
    'https://drive.google.com/file/d/1txWdoLfxFY8S5PoNwfe4ie4PuPMok662/view?usp=sharing',
    'https://drive.google.com/file/d/1txWdoLfxFY8S5PoNwfe4ie4PuPMok662/view?usp=sharing',
    'https://via.placeholder.com/400x150?text=Banner+3',
  ];

  @override
  void initState() {
    super.initState();
    _pageController = PageController(initialPage: _currentPage);

    // Tạo một timer tự động trượt banner
    Timer.periodic(const Duration(seconds: 3), (timer) {
      if (_currentPage < _banners.length - 1) {
        _currentPage++;
      } else {
        _currentPage = 0;
      }
      _pageController.animateToPage(
        _currentPage,
        duration: const Duration(milliseconds: 300),
        curve: Curves.easeInOut,
      );
    });
  }

  @override
  Widget build(BuildContext context) {

    // URL ảnh đại diện (null để kiểm tra trường hợp mặc định)
    const String? avatarUrl = null; // Thay đổi giá trị này để thử nghiệm

    return Scaffold(
      body: SingleChildScrollView(
        child: Column(
          children: [
            // Banner đỏ
            Container(
              height: 116, // Chiều cao banner
              color: const Color.fromARGB(255, 210, 52, 52), // Màu đỏ Viettel Post
              child: Padding(
                padding: const EdgeInsets.symmetric(horizontal: 16.0, vertical: 20.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    // Avatar và họ tên
                    Row(
                      children: [
                        // Avatar với bóng đỏ
                        Container(
                          decoration: BoxDecoration(
                            shape: BoxShape.circle,
                            boxShadow: [
                              BoxShadow(
                                color: Colors.red.withOpacity(0.6), // Màu bóng đỏ
                                spreadRadius: 2, // Độ lan của bóng
                                blurRadius: 10, // Độ mờ của bóng
                                offset: const Offset(0, 5), // Độ lệch của bóng
                              ),
                            ],
                          ),
                          child: CircleAvatar(
                            radius: 35,
                            backgroundColor: Colors.grey[200], // Nền cho ảnh mặc định
                            backgroundImage: avatarUrl != null
                                ? NetworkImage(avatarUrl) // Ảnh người dùng
                                : null, // Không có ảnh
                            child: avatarUrl == null
                                ? const Icon(
                              Icons.person, // Icon mặc định
                              size: 30,
                              color: Colors.grey,
                            )
                                : null,
                          ),
                        ),
                        const SizedBox(width: 10),
                        const Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          mainAxisAlignment: MainAxisAlignment.center,
                          children: [
                            Text(
                              "Nguyễn Văn A",
                              style: TextStyle(
                                color: Colors.white,
                                fontSize: 18,
                                fontWeight: FontWeight.bold,
                              ),
                            ),
                            Text(
                              "Khách hàng thân thiết",
                              style: TextStyle(
                                color: Colors.white70,
                                fontSize: 14,
                              ),
                            ),
                          ],
                        ),
                      ],
                    ),

                    // Tìm kiếm và quét QR
                    Row(
                      children: [
                        // Icon tìm kiếm
                        IconButton(
                          onPressed: () => Navigator.pushNamed(context, '/detailsScanOrder'),

                          icon: const Icon(Icons.search, color: Colors.white),
                        ),
                        // Icon quét QR
                        IconButton(
                          onPressed: () => Navigator.pushNamed(context, '/detailsScanOrder'),
                          icon: const Icon(Icons.qr_code_scanner, color: Colors.white),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
            ),

            // Thông tin tạo đơn hàng mới
            const SizedBox(height: 10),
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 18.0),
              child: Card(
                margin: const EdgeInsets.only(bottom: 10),
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(10.0),
                ),
                elevation: 5,
                child: ListTile(
                  onTap: () {
                    Navigator.pushNamed(context, '/detailsProfile'); // Chuyển đến trang chi tiết
                  },
                  leading: const Icon(
                    Icons.location_on, // Biểu tượng địa điểm
                    color: Colors.red, // Màu đỏ nổi bật
                    size: 28, // Kích thước icon
                  ),
                  title: Text(
                    "1168/9 Đường Lê Văn Lương, X.Phước Kiển, H.Nhà Bè, TP.HCM",
                    style: const TextStyle(
                      fontSize: 14,
                      fontWeight: FontWeight.w600,
                    ),
                    maxLines: 1, // Hiển thị tối đa 1 dòng
                    overflow: TextOverflow.ellipsis, // Thêm "..." nếu dài quá
                  ),
                  subtitle: const Text("Bạn muốn gửi hàng tới?"),
                ),
              ),
            ),


            // Nội dung thêm
            const SizedBox(height: 10),

            // Các dịch vụ tiện ích
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 20.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  const Text(
                    "Dịch vụ tiện ích",
                    style: TextStyle(
                      fontSize: 18,
                      fontWeight: FontWeight.bold,
                      color: Colors.black87,
                    ),
                  ),
                  const SizedBox(height: 10),

                  // Lưới các tiện ích
                  GridView.count(
                    shrinkWrap: true,
                    crossAxisCount: 4, // Hiển thị 4 button trên mỗi hàng
                    crossAxisSpacing: 10,
                    mainAxisSpacing: 10,
                    physics: const NeverScrollableScrollPhysics(), // Tắt cuộn riêng lưới
                    children: [
                      // Button "Tạo đơn hàng"
                      _buildServiceButton(
                        icon: Icons.add_box,
                        color: Colors.blue,
                        label: "Tạo đơn",
                        onPressed: () {
                          _showAlertDialog(context, "Tạo đơn hàng", "Chức năng tạo đơn hàng sẽ được thực hiện ở đây.");
                        },
                      ),
                      // Button "Theo dõi đơn hàng"
                      _buildServiceButton(
                        icon: Icons.local_shipping,
                        color: Colors.green,
                        label: "Theo dõi",
                        onPressed: () {
                          _showAlertDialog(context, "Theo dõi đơn hàng", "Chức năng theo dõi đơn hàng sẽ được thực hiện ở đây.");
                        },
                      ),
                      // Button "Ước tính giá cước"
                      _buildServiceButton(
                        icon: Icons.calculate,
                        color: Colors.orange,
                        label: "Ước tính",
                        onPressed: () {
                          _showAlertDialog(context, "Ước tính giá cước", "Chức năng ước tính giá cước sẽ được thực hiện ở đây.");
                        },
                      ),
                      // Button "Địa chỉ lưu trữ"
                      _buildServiceButton(
                        icon: Icons.bookmark,
                        color: Colors.purple,
                        label: "Lưu trữ",
                        onPressed: () {
                          _showAlertDialog(context, "Địa chỉ lưu trữ", "Chức năng lưu trữ địa chỉ sẽ được thực hiện ở đây.");
                        },
                      ),
                      // Button "Cài đặt"
                      _buildServiceButton(
                        icon: Icons.settings,
                        color: Colors.red,
                        label: "Cài đặt",
                        onPressed: () {
                          _showAlertDialog(context, "Cài đặt", "Chức năng cài đặt sẽ được thực hiện ở đây.");
                        },
                      ),
                    ],
                  ),
                ],
              ),
            ),

            const SizedBox(height: 30),

            // Banner quảng cáo tự động trượt
            Container(
              height: 200, // Chiều cao của banner
              margin: const EdgeInsets.only(bottom: 20),
              child: PageView.builder(
                controller: _pageController,
                itemCount: _banners.length,
                itemBuilder: (context, index) {
                  return Padding(
                    padding: const EdgeInsets.symmetric(horizontal: 15.0),
                    child: ClipRRect(
                      borderRadius: BorderRadius.circular(10),
                      child: Image.network(
                        _banners[index],
                        fit: BoxFit.cover,
                      ),
                    ),
                  );
                },
                onPageChanged: (page) {
                  setState(() {
                    _currentPage = page;
                  });
                },
              ),
            ),


            // Phần nội dung thử nghiệm
            const SizedBox(height: 10),
            Padding(
              padding: const EdgeInsets.all(20.0),
              child: Text(
                "Nội dung thử nghiệm cuộn kéo dài. Đây là ví dụ thêm dữ liệu vào trang.",
                style: TextStyle(fontSize: 16, color: Colors.grey[600]),
                textAlign: TextAlign.center,
              ),
            ),

          ],
        ),
      ),
    );
  }

  Widget _buildServiceButton({
    required IconData icon,
    required Color color,
    required String label,
    required VoidCallback onPressed,
  }) {
    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        GestureDetector(
          onTap: onPressed,
          child: Container(
            padding: const EdgeInsets.all(10),
            decoration: BoxDecoration(
              color: color.withOpacity(0.2), // Nền nhạt theo màu icon
              shape: BoxShape.circle,
            ),
            child: Icon(
              icon,
              color: color,
              size: 30,
            ),
          ),
        ),
        const SizedBox(height: 5),
        Text(
          label,
          style: const TextStyle(fontSize: 12, color: Colors.black),
          textAlign: TextAlign.center,
        ),
      ],
    );
  }

  void _showAlertDialog(BuildContext context, String title, String message) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text(title),
          content: Text(message),
          actions: <Widget>[
            TextButton(
              onPressed: () {
                Navigator.of(context).pop(); // Đóng AlertDialog
              },
              child: const Text("Đóng"),
            ),
          ],
        );
      },
    );
  }
}

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:curved_navigation_bar/curved_navigation_bar.dart';
import 'package:mobile_app/views/navigations/updates_navigation.dart';
import 'package:mobile_app/views/navigations/wishlists_navigation.dart';

class MainWrapper extends StatefulWidget {
  const MainWrapper({super.key});

  @override
  MainWrapperState createState() => MainWrapperState();
}

class MainWrapperState extends State<MainWrapper> {
  int _selectedIndex = 0;

  final List<GlobalKey<NavigatorState>> _navigatorKeys = [
    wishListNavigatorKey,
    updatesNavigatorKey,
    GlobalKey<NavigatorState>(), // Key for Home
    GlobalKey<NavigatorState>(), // Key for Profile
  ];

  final List<Widget> _pages = const <Widget>[
    Wishlist(), // Route 1: Wishlist
    UpdatesNavigator(), // Route 2: Updates
    Placeholder(), // Route 3: Home
    Placeholder(), // Route 4: Profile
  ];

  // Danh sách màu sắc của các biểu tượng
  final List<Color> _iconColors = [
    Colors.white, // Màu mặc định của Wishlist
    Colors.lightBlue, // Màu mặc định của Updates
    Colors.lightBlue, // Màu mặc định của Home
    Colors.lightBlue, // Màu mặc định của Profile
  ];

  Future<bool> _systemBackButtonPressed() async {
    if (_navigatorKeys[_selectedIndex].currentState?.canPop() == true) {
      _navigatorKeys[_selectedIndex]
          .currentState
          ?.pop(_navigatorKeys[_selectedIndex].currentContext);
      return false;
    } else {
      SystemChannels.platform.invokeMethod<void>('SystemNavigator.pop');
      return true; // Indicate that the back action is handled
    }
  }

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: _systemBackButtonPressed,
      child: Scaffold(
        body: IndexedStack(
          index: _selectedIndex,
          children: _pages,
        ),
        bottomNavigationBar: CurvedNavigationBar(
          backgroundColor: Colors.yellow[700]!, // Màu nền: vàng thương hiệu
          color: Colors.white, // Màu thanh điều hướng
          buttonBackgroundColor: Colors.blue[800]!, // Màu nút hiện tại
          height: 60,
          animationDuration: const Duration(milliseconds: 300),
          index: _selectedIndex,
          onTap: (int index) {
            setState(() {
              // Cập nhật màu sắc: nút được chọn thành trắng, các nút khác là LightBlue
              for (int i = 0; i < _iconColors.length; i++) {
                _iconColors[i] = (i == index) ? Colors.white : Colors.lightBlue;
              }
              _selectedIndex = index;
            });
          },
          items: [
            Icon(Icons.favorite, size: 30, color: _iconColors[0]), // Wishlist
            Icon(Icons.notifications, size: 30, color: _iconColors[1]), // Updates
            Icon(Icons.home, size: 30, color: _iconColors[2]), // Home
            Icon(Icons.person, size: 30, color: _iconColors[3]), // Profile
          ],
        ),
      ),
    );
  }
}

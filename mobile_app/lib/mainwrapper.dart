import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:mobile_app/views/navigations/homes_navigation.dart';
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
    Home(), // Route 1: Home
    UpdatesNavigator(), // Route 2: Updates
    Placeholder(), // Route 3: Home
    Placeholder(), // Route 4: Profile
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
        bottomNavigationBar: BottomNavigationBar(
          type: BottomNavigationBarType.fixed, // Không có hiệu ứng
          currentIndex: _selectedIndex,
          onTap: (int index) {
            setState(() {
              _selectedIndex = index;
            });
          },
          selectedItemColor: Colors.red[700], // Màu khi chọn
          unselectedItemColor: Colors.grey, // Màu không chọn
          backgroundColor: Colors.white, // Màu nền
          items: const [
            BottomNavigationBarItem(
              icon: Icon(Icons.home),
              label: 'Home',
            ),
            BottomNavigationBarItem(
              icon: Icon(Icons.receipt_long_sharp),
              label: 'Order',
            ),
            BottomNavigationBarItem(
              icon: Icon(Icons.notifications),
              label: 'Notification',
            ),
            BottomNavigationBarItem(
              icon: Icon(Icons.settings),
              label: 'Setting',
            ),
          ],
        ),
      ),
    );
  }
}

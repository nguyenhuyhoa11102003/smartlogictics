import 'package:flutter/material.dart';
import 'package:mobile_app/views/fragments/homes/detail_profile.dart';
import 'package:mobile_app/views/fragments/homes/detail_scan_qr.dart';
import 'package:mobile_app/views/fragments/homes/homes.dart';

class Home extends StatefulWidget {

  const Home({super.key});

  @override
  State<StatefulWidget> createState() => HomeState();

}

GlobalKey<NavigatorState> homeNavigatorKey = GlobalKey<NavigatorState>();

class HomeState extends State<Home> {
  @override
  Widget build(BuildContext context) {
    // TODO: implement build

    return Navigator(
      key: homeNavigatorKey,
      onGenerateRoute: (RouteSettings settings) {
        return MaterialPageRoute(
          settings: settings,
          builder: (BuildContext context) {

            if (settings.name == "/detailsProfile") {
              return const DetailsProfileView();
            } else if (settings.name == "/detailsScanOrder") {
              return const DetailsScanQRView();
            }

            return const HomeView();
        });
      },
    );

  }

}

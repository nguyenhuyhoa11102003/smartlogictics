import 'package:flutter/material.dart';

class ModalScreen extends StatelessWidget {
  const ModalScreen({super.key});

  @override
  Widget build(BuildContext context) {
    // TODO: implement build

    return WillPopScope(
      // Chặn hành động back
      onWillPop: () async => false,
      child: Dialog(
        backgroundColor: Colors.black.withOpacity(0.7), // Nền mờ
        insetPadding: EdgeInsets.zero, // Full màn hình
        child: SizedBox(
          height: MediaQuery.of(context).size.height,
          child: const Center(
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: [
                CircularProgressIndicator(color: Colors.white),
                SizedBox(height: 20),
                Text(
                  "Fetching data, please wait...",
                  style: TextStyle(
                    color: Colors.white,
                    fontSize: 16,
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
  
}
import 'dart:ui';
import 'package:flutter/material.dart';
import 'package:animated_background/animated_background.dart';
import 'package:mobile_app/utils/animations.dart';
import 'package:mobile_app/models/data/bg_data.dart';
import 'package:mobile_app/utils/text_utils.dart';
import 'package:mobile_app/views/screens/home_screen.dart';

class RegisterScreen extends StatefulWidget {
  const RegisterScreen({super.key});

  @override
  State<StatefulWidget> createState() => _RegisterScreen();
}

class _RegisterScreen extends State<RegisterScreen> with TickerProviderStateMixin {
  int selectedIndex = 0;
  bool showOption = false;

  // Controllers for new fields
  final TextEditingController emailController = TextEditingController();
  final TextEditingController passwordController = TextEditingController();
  final TextEditingController nameController = TextEditingController();
  final TextEditingController phoneController = TextEditingController();
  final TextEditingController addressController = TextEditingController();

  void registration() {
    String email = emailController.text.trim();
    String password = passwordController.text.trim();
    String name = nameController.text.trim();
    String phone = phoneController.text.trim();
    String address = addressController.text.trim();

    if (email.isEmpty || password.isEmpty || name.isEmpty || phone.isEmpty || address.isEmpty) {
      _showDialog("Error", "All fields are required.");
    } else {
      // Simulate successful registration (this should be replaced with actual logic)
      Navigator.pushReplacement(
        context,
        MaterialPageRoute(builder: (context) => const HomeScreen(title: "Fast Delivery")),
      );
    }
  }

  void _showDialog(String title, String content) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: Text(title),
        content: Text(content),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: const Text("OK"),
          ),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Stack(
        children: [
          // Background image
          Container(
            height: double.infinity,
            width: double.infinity,
            decoration: BoxDecoration(
              image: DecorationImage(
                image: AssetImage(bgList[selectedIndex]),
                fit: BoxFit.cover,
              ),
            ),
          ),
          // Animated background effect
          AnimatedBackground(
            behaviour: RandomParticleBehaviour(
              options: const ParticleOptions(
                baseColor: Colors.white,
                spawnMaxRadius: 18,
                spawnMaxSpeed: 50, // Adjusted value
                particleCount: 18,
                spawnMinSpeed: 30, // Adjusted value
                minOpacity: 0.3,
                spawnOpacity: 0.4,
                image: Image(image: AssetImage('assets/snowflake.png')),
              ),
            ),
            vsync: this,
            child: Container(
              height: double.infinity,
              width: double.infinity,
            ),
          ),
          // Registration form
          Center(
            child: Container(
              height: 500, // Adjusted height for more fields
              width: double.infinity,
              margin: const EdgeInsets.symmetric(horizontal: 30),
              decoration: BoxDecoration(
                border: Border.all(color: Colors.white),
                borderRadius: BorderRadius.circular(15),
                color: Colors.black.withOpacity(0.1),
              ),
              child: ClipRRect(
                borderRadius: BorderRadius.circular(20),
                child: BackdropFilter(
                  filter: ImageFilter.blur(sigmaY: 5, sigmaX: 5),
                  child: Padding(
                    padding: const EdgeInsets.all(25),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        const Spacer(),
                        Center(
                          child: TextUtil(
                            text: "Registration",
                            weight: true,
                            size: 30,
                          ),
                        ),
                        const Spacer(),
                        // Name field
                        TextUtil(text: "Full Name"),
                        Container(
                          height: 35,
                          decoration: const BoxDecoration(
                            border: Border(bottom: BorderSide(color: Colors.white)),
                          ),
                          child: TextFormField(
                            controller: nameController,
                            style: const TextStyle(color: Colors.white),
                            decoration: const InputDecoration(
                              suffixIcon: Icon(Icons.person, color: Colors.white),
                              fillColor: Colors.white,
                              border: InputBorder.none,
                            ),
                          ),
                        ),
                        const Spacer(),
                        // Phone field
                        TextUtil(text: "Phone Number"),
                        Container(
                          height: 35,
                          decoration: const BoxDecoration(
                            border: Border(bottom: BorderSide(color: Colors.white)),
                          ),
                          child: TextFormField(
                            controller: phoneController,
                            keyboardType: TextInputType.phone,
                            style: const TextStyle(color: Colors.white),
                            decoration: const InputDecoration(
                              suffixIcon: Icon(Icons.phone, color: Colors.white),
                              fillColor: Colors.white,
                              border: InputBorder.none,
                            ),
                          ),
                        ),
                        const Spacer(),
                        // Address field
                        TextUtil(text: "Address"),
                        Container(
                          height: 35,
                          decoration: const BoxDecoration(
                            border: Border(bottom: BorderSide(color: Colors.white)),
                          ),
                          child: TextFormField(
                            controller: addressController,
                            style: const TextStyle(color: Colors.white),
                            decoration: const InputDecoration(
                              suffixIcon: Icon(Icons.location_on, color: Colors.white),
                              fillColor: Colors.white,
                              border: InputBorder.none,
                            ),
                          ),
                        ),
                        const Spacer(),
                        // Email field
                        TextUtil(text: "Email"),
                        Container(
                          height: 35,
                          decoration: const BoxDecoration(
                            border: Border(bottom: BorderSide(color: Colors.white)),
                          ),
                          child: TextFormField(
                            controller: emailController,
                            style: const TextStyle(color: Colors.white),
                            decoration: const InputDecoration(
                              suffixIcon: Icon(Icons.mail, color: Colors.white),
                              fillColor: Colors.white,
                              border: InputBorder.none,
                            ),
                          ),
                        ),
                        const Spacer(),
                        // Password field
                        TextUtil(text: "Password"),
                        Container(
                          height: 35,
                          decoration: const BoxDecoration(
                            border: Border(bottom: BorderSide(color: Colors.white)),
                          ),
                          child: TextFormField(
                            controller: passwordController,
                            obscureText: true,
                            style: const TextStyle(color: Colors.white),
                            decoration: const InputDecoration(
                              suffixIcon: Icon(Icons.lock, color: Colors.white),
                              fillColor: Colors.white,
                              border: InputBorder.none,
                            ),
                          ),
                        ),
                        const Spacer(),
                        // Register button
                        GestureDetector(
                          onTap: registration,
                          child: Container(
                            height: 40,
                            width: double.infinity,
                            decoration: BoxDecoration(
                              color: Colors.white,
                              borderRadius: BorderRadius.circular(30),
                            ),
                            alignment: Alignment.center,
                            child: TextUtil(
                              text: "Register",
                              color: Colors.black,
                            ),
                          ),
                        ),
                        const Spacer(),
                        Center(
                          child: TextUtil(
                            text: "Already have an account? LOGIN",
                            size: 12,
                            weight: true,
                          ),
                        ),
                        const Spacer(),
                      ],
                    ),
                  ),
                ),
              ),
            ),
          ),
        ],
      ),
      // Background image selection floating action button
      floatingActionButton: Container(
        margin: const EdgeInsets.symmetric(vertical: 10),
        height: 49,
        width: double.infinity,
        child: Row(
          children: [
            Expanded(
              child: showOption
                  ? ShowUpAnimation(
                delay: 100,
                child: ListView.builder(
                  shrinkWrap: true,
                  itemCount: bgList.length,
                  scrollDirection: Axis.horizontal,
                  itemBuilder: (context, index) {
                    return GestureDetector(
                      onTap: () {
                        setState(() {
                          selectedIndex = index;
                        });
                      },
                      child: CircleAvatar(
                        radius: 30,
                        backgroundColor: selectedIndex == index ? Colors.white : Colors.transparent,
                        child: Padding(
                          padding: const EdgeInsets.all(1),
                          child: CircleAvatar(
                            radius: 30,
                            backgroundImage: AssetImage(bgList[index]),
                          ),
                        ),
                      ),
                    );
                  },
                ),
              )
                  : const SizedBox(),
            ),
            const SizedBox(width: 20),
            GestureDetector(
              onTap: () {
                setState(() {
                  showOption = !showOption;
                });
              },
              child: CircleAvatar(
                backgroundColor: Colors.white,
                child: Padding(
                  padding: const EdgeInsets.all(1),
                  child: CircleAvatar(
                    radius: 30,
                    backgroundImage: AssetImage(bgList[selectedIndex]),
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}

const express = require('express');
const pool = require('./db')

const app = express()
const PORT = 3001;
app.use(express.json());
app.get('/', (req, res) => {
    res.send("Hello to the root - The Booking Engine is Online!");
});

app.post('/api/auth/login', async (req, res) => {
    try {
        const { emailInput, passwordInput } = req.body;

        const userResult = await pool.query(
            'SELECT * FROM users WHERE email = $1', 
            [emailInput]
        );

        if (userResult.rows.length === 0) {
            return res.status(404).json({ message: "User not found" });
        }

        const user = userResult.rows[0];

        const validPassword = passwordInput===user.password_hash? true : false; //later, encrypt the input then compare 

        if (!validPassword) {
            return res.status(401).json({ message: "Wrong Password" });
        }
        res.status(200).json({ 
            message: "Login Successful",
            user: {
                id: user.user_id,
                name: user.full_name,
                role: user.role
            }
        });

    } catch (error) {
        console.error("Login error:", error);
        res.status(500).json({ message: "Internal server error" });
    }
});

app.post('/api/auth/register', async (req, res) => {
    try {
        const { full_name, email, phone, password, role } = req.body;

        if (!full_name || !email || !password || !role) {
            return res.status(400).json({ message: "Missing required fields" });
        }
        const insertUserQuery = `
            INSERT INTO users (full_name, email, phone, password_hash, role) 
            VALUES ($1, $2, $3, $4, $5) 
            RETURNING user_id, full_name, email, role, created_at
        `;
        const newUserResult = await pool.query(insertUserQuery, [
            full_name, 
            email, 
            phone, 
            password, // Later, Wrap this in bcrypt.hash()
            role
        ]);
        const createdUser = newUserResult.rows[0];

        res.status(201).json({
            message: "User registered successfully",
            user: createdUser
        });

    } catch (error) {
        console.error("Registration error:", error);
        if (error.code === '23505') {
            return res.status(409).json({ 
                message: "Registration failed: Email or phone number already in use." 
            });
        }
        res.status(500).json({ message: "Internal server error" });
    }
});

async function startServer() {
    try {
        await pool.query('SELECT 1');
        console.log("Postgres Database connected successfully");

        app.listen(PORT, () => {
            console.log(`Server started on http://localhost:${PORT}`);
        });

    } catch (err) {
        console.error("Failed to connect to Database:", err);
        process.exit(1); 
    }
}
startServer();
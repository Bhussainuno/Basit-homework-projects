import os
import discord
from discord.ext import commands

# Get the bot token from AWS Lambda environment variables
TOKEN = os.getenv("DISCORD_TOKEN")

intents = discord.Intents.default()
bot = commands.Bot(command_prefix="!", intents=intents)

@bot.event
async def on_ready():
    print(f"Logged in as {bot.user}")

@bot.command()
async def hello(ctx):
    await ctx.send("Hello! I'm a Terraform-deployed bot.")

# Run the bot
bot.run(TOKEN)

import * as React from "react"
import { ChevronRight } from "lucide-react"
import { SearchForm } from "@/components/search-form"
import {
    Collapsible,
    CollapsibleContent,
    CollapsibleTrigger,
} from "@/components/ui/collapsible"
import {
    Sidebar,
    SidebarContent,
    SidebarGroup,
    SidebarGroupContent,
    SidebarGroupLabel,
    SidebarHeader,
    SidebarMenu,
    SidebarMenuButton,
    SidebarMenuItem,
    SidebarRail,
} from "@/components/ui/sidebar"

const data = {
    navMain: [
        {
            title: "Trang chủ",
            url: "#",
            items: [

            ],
        },
        {
            title: "Tạo đơn",
            url: "#",
            items: [
                {
                    title: "Tạo đơn lẻ",
                    url: "#",
                },
                {
                    title: "Nhập Excel",
                    url: "#",
                    isActive: true,
                },
            ],
        },
        {
            title: "Quản lý",
            url: "#",
            items: [
                {
                    title: "Quản lý vận đơn",
                    url: "#",
                },
                {
                    title: "Thống kê tiền hàng ",
                    url: "#",
                },
                {
                    title: "Thống kê doanh thu",
                    url: "#",
                },
                {
                    title: "Đơn hàng cần xử lý",
                    url: "#",
                },
                {
                    title: "Danh sách người nhận",
                    url: "#",
                },
            ],
        },
        {
            title: "Tra cứu",
            url: "#",
            items: [
                {
                    title: "Tra cứu bưu cục",
                    url: "#",
                },
                {
                    title: "ƯỚC PHÍ",
                    url: "#",
                },
            ],
        },
        {
            title: "Cài đặt tài khoản",
            url: "#",
            items: [],
        },
    ],
}


export function AppSidebar({ ...props }: React.ComponentProps<typeof Sidebar>) {
    return (
        <Sidebar {...props}>
            <SidebarHeader>
                <SearchForm />
            </SidebarHeader>
            <SidebarContent className="gap-0">
                {/* We create a collapsible SidebarGroup for each parent. */}
                {data.navMain.map((item) => (
                    <Collapsible
                        key={item.title}
                        title={item.title}
                        defaultOpen
                        className="group/collapsible"
                    >
                        <SidebarGroup>
                            <SidebarGroupLabel
                                asChild
                                className="group/label text-sm text-sidebar-foreground hover:bg-sidebar-accent hover:text-sidebar-accent-foreground"
                            >
                                <CollapsibleTrigger>
                                    {item.title}{" "}
                                    <ChevronRight className="ml-auto transition-transform group-data-[state=open]/collapsible:rotate-90" />
                                </CollapsibleTrigger>
                            </SidebarGroupLabel>
                            <CollapsibleContent>
                                <SidebarGroupContent>
                                    <SidebarMenu>
                                        {item.items.map((item) => (
                                            <SidebarMenuItem key={item.title}>
                                                <SidebarMenuButton asChild isActive={item.isActive}>
                                                    <a href={item.url}>{item.title}</a>
                                                </SidebarMenuButton>
                                            </SidebarMenuItem>
                                        ))}
                                    </SidebarMenu>
                                </SidebarGroupContent>
                            </CollapsibleContent>
                        </SidebarGroup>
                    </Collapsible>
                ))}
            </SidebarContent>
            <SidebarRail />
        </Sidebar>

    )
}
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


const URL_ADMIN = "/admin"

const data = {
    navMain: [
        {
            title: "Trang chủ",
            url: `${URL_ADMIN}/`,
            items: [],
            content: "Trang chủ content"
        },
        {
            title: "Tạo đơn",
            url: `${URL_ADMIN}/create-order`,
            items: [
                { title: "Tạo đơn lẻ", url: `${URL_ADMIN}/create-order/one`, content: "Tạo đơn content" },
                { title: "Nhập Excel", url: `${URL_ADMIN}/create-order/multi`, content: "Nhập Excel content", isActive: true },
            ]
        },
        {
            title: "Quản lý",
            url: `${URL_ADMIN}/quan-ly-van-don`,
            items: [
                { title: "Quản lý vận đơn", url: `${URL_ADMIN}/quan-ly-van-don`, content: "Quản lý vận đơn content" },
                { title: "Thống kê tiền hàng", url: "/statistics", content: "Thống kê tiền hàng content" },
            ]
        },
    ]
}


export function AppSidebar({ ...props }: React.ComponentProps<typeof Sidebar>) {
    const handleClicked = () => {

    }
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
                                        {item.items.map((subItem) => (
                                            <SidebarMenuItem key={item.title}>
                                                <SidebarMenuButton
                                                    asChild
                                                    isActive={subItem.isActive}
                                                    onClick={handleClicked}
                                                >
                                                    <a href={subItem.url}>{subItem.title}</a>
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